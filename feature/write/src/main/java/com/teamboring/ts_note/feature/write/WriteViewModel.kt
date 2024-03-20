package com.teamboring.ts_note.feature.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamboring.ts_note.core.room.Note
import com.teamboring.ts_note.core.room.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(private val dao: NoteDao) : ViewModel() {

    private val _note = MutableStateFlow(Note())
    val note: StateFlow<Note> = _note

    private val _state = MutableStateFlow(SaveState.IDLE)
    val state: StateFlow<SaveState> = _state

    fun save(title: String, content: String) = viewModelScope.launch(Dispatchers.IO) {
        dao.insertAll(
            Note(
                title = title,
                content = content
            )
        )
        _state.value = SaveState.SUCCESS
    }

    fun loadData(noteId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = SaveState.LOADING
        dao.findById(noteId).collect { note ->
            _note.value = note
            _state.value = SaveState.LOADED
        }
    }

    fun update(title: String, text: String) = viewModelScope.launch(Dispatchers.IO) {
        val newNote = Note(noteId = _note.value.noteId, title = title, content = text)
        dao.update(newNote).apply {
            if (this == 1) {
                _state.value = SaveState.SUCCESS
            } else {
                _state.value = SaveState.ERROR
            }
        }
    }
}

enum class SaveState {
    IDLE, LOADING, LOADED, SUCCESS, ERROR
}


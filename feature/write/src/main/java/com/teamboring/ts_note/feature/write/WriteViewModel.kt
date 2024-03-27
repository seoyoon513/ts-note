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

    private val _state = MutableStateFlow(NoteState(NoteLoadState.IDLE))
    val state: StateFlow<NoteState> = _state

    fun save(title: String, content: String) = viewModelScope.launch(Dispatchers.IO) {
        val note = Note(
            title = title,
            content = content
        )
        dao.insertAll(note)
        _state.value = NoteState(NoteLoadState.ADDED, note)
    }

    fun loadData(noteId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = NoteState(NoteLoadState.LOADING)
        dao.findById(noteId).collect { note ->
            _state.value = NoteState(NoteLoadState.ADDED, note)
        }
    }

    fun update(title: String, text: String) = viewModelScope.launch(Dispatchers.IO) {
        val newNote = Note(noteId = state.value.note.noteId, title = title, content = text)
        dao.update(newNote).apply {
            if (this == 1) {
                _state.value = NoteState(NoteLoadState.UPDATED, newNote)
            } else {
                _state.value = NoteState(NoteLoadState.ERROR, newNote)
            }
        }
    }
}

data class NoteState(val loadState: NoteLoadState, val note: Note = Note())

enum class NoteLoadState {
    IDLE, LOADING, ADDED, UPDATED, ERROR
}
package com.teamboring.ts_note.feature.update

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
class UpdateViewModel @Inject constructor(private val dao: NoteDao): ViewModel() {

    private val _note = MutableStateFlow(Note())
    val note: StateFlow<Note> = _note

    private val _state = MutableStateFlow(UpdateState.IDLE)
    val state: StateFlow<UpdateState> = _state

    fun loadData(noteId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = UpdateState.LOADING
        dao.findById(noteId).collect { note ->
            _note.value = note
            _state.value = UpdateState.LOADED
        }
    }

    fun update(title: String, text: String) = viewModelScope.launch(Dispatchers.IO) {
        val newNote = _note.value.copy(title = title, content = text)
        dao.update(newNote).apply {
            if (this == 1) {
                _state.value = UpdateState.SUCCESS
            } else {
                _state.value = UpdateState.ERROR
            }
        }
    }
}

enum class UpdateState {
    IDLE, LOADING, LOADED, SUCCESS, ERROR
}

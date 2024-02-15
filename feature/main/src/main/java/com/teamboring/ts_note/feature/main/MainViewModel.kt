package com.teamboring.ts_note.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamboring.ts_note.common.ResultState
import com.teamboring.ts_note.core.room.Note
import com.teamboring.ts_note.core.room.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dao: NoteDao) : ViewModel() {

    private val _notes = MutableStateFlow<ResultState<MutableList<Note>>>(ResultState.Empty())
    val notes: StateFlow<ResultState<MutableList<Note>>> = _notes

    init {
        getAllNotes()
    }

    fun getAllNotes() = viewModelScope.launch {
        _notes.value = ResultState.Loading()
        dao.getAll().catch {
            _notes.value = ResultState.Error()
        }.collect { notes ->
            if (notes.isEmpty()) {
                _notes.value = ResultState.Empty()
            } else {
                _notes.value = ResultState.Success(notes)
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(note)
        }
    }
}
package com.teamboring.ts_note.feature.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamboring.ts_note.core.room.Note
import com.teamboring.ts_note.core.room.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(private val dao: NoteDao) : ViewModel() {

    fun save(title: String, content: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertAll(Note(
                title = title,
                content = content
            ))
        }
    }
}

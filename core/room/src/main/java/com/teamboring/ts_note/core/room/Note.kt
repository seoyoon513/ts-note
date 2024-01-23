package com.teamboring.ts_note.core.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.teamboring.ts_note.common.getCurrentTimeText

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val noteId: Int = 0,
    @ColumnInfo val title: String = "",
    @ColumnInfo val content: String = "",
    @ColumnInfo val date: String = getCurrentTimeText(),
)

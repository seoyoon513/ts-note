package com.teamboring.ts_note.core.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): Flow<MutableList<Note>>

    @Query("SELECT * FROM note WHERE noteId = :noteId LIMIT 1")
    fun findById(noteId: Int): Flow<Note>

    @Update
    fun update(note: Note): Int

    @Insert
    fun insertAll(vararg notes: Note)

    @Delete
    fun delete(note: Note)
}
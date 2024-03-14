package com.teamboring.ts_note.core.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var noteDao: NoteDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        noteDao = db.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAllTest() = runTest {
        val note = Note(
            title = "testTitle",
            content = "testContent",
        )
        noteDao.insertAll(note)

        val allNotes = noteDao.getAll().first()

        assert(allNotes[0].title == "testTitle")
        assert(allNotes[0].content == "testContent")
    }

    @Test
    fun updateTest() = runTest {
        val note = Note(
            noteId = 3,
            title = "testTitle",
            content = "testContent",
        )
        noteDao.insertAll(note)
        val result = noteDao.update(note.copy(title = "newTitle"))
        assert(result == 1)

        val allNotes = noteDao.getAll().first()
        assert(allNotes[0].title == "newTitle")
    }


    @Test
    fun deleteTest() = runTest {
        val note = Note(
            noteId = 3,
            title = "testTitle",
            content = "testContent",
        )
        noteDao.insertAll(note)
        noteDao.delete(note)

        val allNotes = noteDao.getAll().first()
        assert(allNotes.isEmpty())
    }


    @Test
    fun findByIdTest() = runTest {
        val noteId = 3
        val note = Note(
            noteId = noteId,
            title = "testTitle",
            content = "testContent",
        )
        noteDao.insertAll(note)

        val foundNote = noteDao.findById(noteId).first()
        assert(foundNote.title == "testTitle")
        assert(foundNote.content == "testContent")
    }
}
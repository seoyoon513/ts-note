package com.teamboring.ts_note.core.room

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDao(application: Application): NoteDao {
        return Room.databaseBuilder(application, AppDatabase::class.java, "note-db")
            .build()
            .noteDao()
    }

}
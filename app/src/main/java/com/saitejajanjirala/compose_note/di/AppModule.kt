package com.saitejajanjirala.compose_note.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.saitejajanjirala.compose_note.data.db.NotesDatabase
import com.saitejajanjirala.compose_note.data.db.ImageDao
import com.saitejajanjirala.compose_note.data.db.NoteDao
import com.saitejajanjirala.compose_note.data.repository.ImageRepositoryImpl
import com.saitejajanjirala.compose_note.data.repository.NotesRepositoryImpl
import com.saitejajanjirala.compose_note.domain.repository.ImageRepository
import com.saitejajanjirala.compose_note.domain.repository.NotesRepository
import com.saitejajanjirala.compose_note.domain.usecases.noteusecase.AddNote
import com.saitejajanjirala.compose_note.domain.usecases.noteusecase.DeleteNote
import com.saitejajanjirala.compose_note.domain.usecases.noteusecase.GetAllNotes
import com.saitejajanjirala.compose_note.domain.usecases.noteusecase.GetNoteById
import com.saitejajanjirala.compose_note.domain.usecases.noteusecase.NoteUseCases
import com.saitejajanjirala.compose_note.domain.usecases.noteusecase.UpdateNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application) : NotesDatabase{
        return Room
            .databaseBuilder(application,NotesDatabase::class.java,NotesDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesNoteDao(notesDatabase: NotesDatabase) : NoteDao {
        return notesDatabase.noteDao
    }

    @Provides
    @Singleton
    fun providesImagesDao(notesDatabase: NotesDatabase): ImageDao {
        return notesDatabase.imageDao
    }

    @Provides
    @Singleton
    fun providesNotesRepository(noteDao: NoteDao) : NotesRepository{
        return NotesRepositoryImpl(noteDao)
    }

    @Provides
    @Singleton
    fun providesImageRepository(imageDao: ImageDao):ImageRepository{
        return ImageRepositoryImpl(imageDao)
    }

    @Provides
    @Singleton
    fun providesNoteUseCases(notesRepository : NotesRepository):NoteUseCases{
        return NoteUseCases(
            addNote = AddNote(notesRepository),
            deleteNote = DeleteNote(notesRepository),
            updateNote = UpdateNote(notesRepository),
            getAllNotes = GetAllNotes(notesRepository),
            getNoteById = GetNoteById(notesRepository),
        )
    }

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }


}
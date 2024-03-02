package com.saitejajanjirala.compose_note.data.repository

import com.saitejajanjirala.compose_note.data.db.NoteDao
import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(val noteDao: NoteDao) : NotesRepository{
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
       noteDao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    override suspend fun getNoteById(noteId: Int): Note {
        return noteDao.getNoteById(noteId)
    }

}
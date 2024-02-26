package com.saitejajanjirala.compose_note.domain.repository

import com.saitejajanjirala.compose_note.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun getNoteById(noteId : Int) : Note
}
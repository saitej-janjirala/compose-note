package com.saitejajanjirala.compose_note.domain.usecases.noteusecase

import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.repository.NotesRepository

class GetNoteById (private val notesRepository: NotesRepository) {

    suspend fun invoke(noteId : Int): Note {
        return notesRepository.getNoteById(noteId)
    }

}
package com.saitejajanjirala.compose_note.domain.usecases.noteusecase

import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.repository.NotesRepository

class DeleteNote (private val notesRepository: NotesRepository) {
    suspend fun invoke(note: Note){
        notesRepository.deleteNote(note)
    }
}
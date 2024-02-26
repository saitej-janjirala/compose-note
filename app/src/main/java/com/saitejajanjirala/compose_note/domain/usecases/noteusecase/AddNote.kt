package com.saitejajanjirala.compose_note.domain.usecases.noteusecase

import com.saitejajanjirala.compose_note.domain.models.InvalidNoteException
import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.repository.NotesRepository

class AddNote (private val notesRepository: NotesRepository){
    suspend fun invoke(note : Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("Title Cannot be blank")
        }
        if(note.description.isBlank()){
            throw InvalidNoteException("Description Cannot be blank")
        }
        notesRepository.insertNote(note)
    }
}
package com.saitejajanjirala.compose_note.domain.usecases.noteusecase

import com.saitejajanjirala.compose_note.domain.models.InvalidNoteException
import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.repository.NotesRepository

class AddNote (private val notesRepository: NotesRepository){
    suspend fun invoke(note : Note) : Long{
        var isAnyTrue = false
        if(note.title.isNotBlank()){
            isAnyTrue = true
        }
        if(!note.description.isNotBlank()){
            isAnyTrue = true
        }
        if(note.images.isNotEmpty()){
            isAnyTrue = true
        }
        if(isAnyTrue) {
            return notesRepository.insertNote(note)
        }
        else{
            throw InvalidNoteException("Note shouldn't be empty")
        }
    }
}
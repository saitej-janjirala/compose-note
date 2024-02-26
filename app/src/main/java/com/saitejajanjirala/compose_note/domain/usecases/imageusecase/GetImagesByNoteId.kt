package com.saitejajanjirala.compose_note.domain.usecases.imageusecase

import com.saitejajanjirala.compose_note.domain.models.ImageModel
import com.saitejajanjirala.compose_note.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

class GetImagesByNoteId constructor(private val repository: ImageRepository)
{
    fun invoke(noteId : Int): Flow<List<ImageModel>> {
        return repository.getAllImagesByNoteId(noteId)
    }
}
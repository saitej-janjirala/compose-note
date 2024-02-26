package com.saitejajanjirala.compose_note.domain.repository

import com.saitejajanjirala.compose_note.domain.models.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun insert(imageModel: ImageModel)

    suspend fun delete(imageModel: ImageModel)

    fun getAllImagesByNoteId(noteId : Int): Flow<List<ImageModel>>
}
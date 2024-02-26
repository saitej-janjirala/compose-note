package com.saitejajanjirala.compose_note.domain.usecases.imageusecase

import com.saitejajanjirala.compose_note.domain.models.ImageModel
import com.saitejajanjirala.compose_note.domain.repository.ImageRepository

class DeleteImage constructor(private val imageRepository: ImageRepository){
    suspend fun invoke(imageModel: ImageModel){
        imageRepository.delete(imageModel)
    }
}
package com.saitejajanjirala.compose_note.domain.usecases.imageusecase

data class ImageUseCases(
    val addImage: AddImage,
    val deleteImage: DeleteImage,
    val getImagesByNoteId: GetImagesByNoteId
)

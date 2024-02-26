package com.saitejajanjirala.compose_note.presentation.addeditnotes

import com.saitejajanjirala.compose_note.domain.models.ImageModel

sealed class ImageEvent {
    data class AddImage(var imageModel :ImageModel) : ImageEvent()
    data class DeleteImage(var imageModel: ImageModel) : ImageEvent()
}
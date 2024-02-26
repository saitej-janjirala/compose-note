package com.saitejajanjirala.compose_note.presentation.addeditnotes

import com.saitejajanjirala.compose_note.domain.models.ImageModel

data class NoteImagesState(
    val images: List<ImageModel> = mutableListOf<ImageModel>(),
) {

}
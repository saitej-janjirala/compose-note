package com.saitejajanjirala.compose_note.presentation.addeditnotes

import android.net.Uri
import com.saitejajanjirala.compose_note.domain.models.ImageModel

sealed class ImageEvent {
    data class AddImage(var uri : Uri) : ImageEvent()
    data class DeleteImage(var uri: Uri) : ImageEvent()
}
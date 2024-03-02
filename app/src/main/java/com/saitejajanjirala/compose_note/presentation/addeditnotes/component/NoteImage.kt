package com.saitejajanjirala.compose_note.presentation.addeditnotes.component

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.saitejajanjirala.compose_note.domain.converters.Converters
import com.saitejajanjirala.compose_note.domain.models.ImageModel

@Composable
fun NoteImage(
    modifier: Modifier = Modifier,
    onDeleteClick : (Uri) -> Unit,
    uri: Uri
){
    ElevatedCard(
        modifier = Modifier.height(150.dp)
    ) {
        Box {
            val converters = Converters()
            AsyncImage(
                model = uri,
                "note-image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = { onDeleteClick(uri) },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    tint = Color.Red
                )
            }

        }
    }

}
package com.saitejajanjirala.compose_note.presentation.notes.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.saitejajanjirala.compose_note.domain.models.Note
import java.nio.file.WatchEvent

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onDeleteClick : (Note)->Unit
    ){
    Box(
      modifier = modifier
    ){
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
                )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        IconButton(
            onClick = {
                onDeleteClick(note)
            },
            modifier = Modifier.align(Alignment.BottomEnd)
            ) {
            Icon(
              imageVector = Icons.Default.Delete,
                contentDescription = "Delete clicked"
            )
        }
    }
}
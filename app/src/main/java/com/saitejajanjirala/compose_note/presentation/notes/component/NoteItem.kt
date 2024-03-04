package com.saitejajanjirala.compose_note.presentation.notes.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.util.DateUtils

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
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
                )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(thickness = 2.dp)
            Text(
                text = note.description,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = DateUtils.getDateFromTimeInMillis(note.timeStamp),
                color = Color.Gray,
                fontSize = 12.sp
            )
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
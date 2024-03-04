package com.saitejajanjirala.compose_note.presentation.addeditnotes.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteSaveDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    isOpened: Boolean,
    onConfirmCallback: () -> Unit,
    onDismissCallback : ()->Unit
){
    if(isOpened) {
        AlertDialog(
            onDismissRequest = { onDismissCallback() },
            title = {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Text(
                    text = description,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { onConfirmCallback() },
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "Confirm",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onDismissCallback() },
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "Dismiss",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
        )
    }

}
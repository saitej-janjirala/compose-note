package com.saitejajanjirala.compose_note.presentation.notes.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DefaultRadioButton(
    isSelected : Boolean,
    text : String,
    onSelect : ()->Unit,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.onPrimary,
                unselectedColor = MaterialTheme.colorScheme.background
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}
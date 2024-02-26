package com.saitejajanjirala.compose_note.presentation.addeditnotes.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TransparentTextField(
    isHintVisible : Boolean,
    onFocusChange : (FocusState)->Unit,
    onValueChange : (String) -> Unit,
    hint : String,
    text : String,
    textStyle : TextStyle = TextStyle(),
    singleLine : Boolean = false
){
    Box {

        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .onFocusChanged {
                    onFocusChange(it)
                }
        )
        if (isHintVisible) {
            Text(text = hint, style = textStyle, color = Color.DarkGray, modifier = Modifier.align(
                Alignment.CenterStart
            ))
        }
    }
}
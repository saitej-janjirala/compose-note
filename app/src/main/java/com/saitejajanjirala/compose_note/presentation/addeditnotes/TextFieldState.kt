package com.saitejajanjirala.compose_note.presentation.addeditnotes

data class TextFieldState(
    var text : String="",
    var hint : String = "",
    var isHintVisible : Boolean = true
)
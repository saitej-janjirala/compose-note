package com.saitejajanjirala.compose_note.presentation.addeditnotes

import androidx.compose.ui.focus.FocusState

sealed class AddEditNotesEvent {
    data class OnTitleEntered(val text : String) : AddEditNotesEvent()
    data class OnDescriptionEntered(val text : String) : AddEditNotesEvent()
    data class OnTitleFocusChanged(val focusState: FocusState) : AddEditNotesEvent()
    data class OnDescriptionFocusChanged(val focusState: FocusState): AddEditNotesEvent()
    object OnSaveNote : AddEditNotesEvent()
}
package com.saitejajanjirala.compose_note.presentation.notes

import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class OrderEvent(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note : Note) : NotesEvent()
    object RestoreNoteEvent : NotesEvent()
    object ToggleOrderSelection : NotesEvent()
}
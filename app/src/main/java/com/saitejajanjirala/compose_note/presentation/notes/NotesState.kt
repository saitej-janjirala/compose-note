package com.saitejajanjirala.compose_note.presentation.notes

import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.util.NoteOrder
import com.saitejajanjirala.compose_note.domain.util.OrderType

data class NotesState(
    val notes : List<Note> = emptyList(),
    val isOrderSectionVisible : Boolean = false,
    val noteOrder : NoteOrder = NoteOrder.Date(OrderType.DESCENDING)
)

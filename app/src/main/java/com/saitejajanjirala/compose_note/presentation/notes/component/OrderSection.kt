package com.saitejajanjirala.compose_note.presentation.notes.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saitejajanjirala.compose_note.domain.util.NoteOrder
import com.saitejajanjirala.compose_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder,
    onOrderChange : (NoteOrder)->Unit
){
    Column {
        Row(modifier = Modifier.fillMaxWidth()){
            DefaultRadioButton(
                isSelected = noteOrder is NoteOrder.Title,
                text = "Title",
                onSelect = {
                    onOrderChange(NoteOrder.Title(noteOrder.orderType))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                isSelected = noteOrder is NoteOrder.Date,
                text = "Date",
                onSelect = {
                    onOrderChange(NoteOrder.Date(noteOrder.orderType))
                }
            )
        }
        Row(modifier = Modifier.fillMaxWidth()){
            DefaultRadioButton(
                isSelected = noteOrder.orderType is OrderType.ASCENDING,
                text = "Ascending",
                onSelect = {
                    onOrderChange(noteOrder.copyOrderType(OrderType.ASCENDING))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                isSelected = noteOrder.orderType is OrderType.DESCENDING,
                text = "Descending",
                onSelect = {
                    onOrderChange(noteOrder.copyOrderType(OrderType.DESCENDING))
                }
            )
        }
    }
}
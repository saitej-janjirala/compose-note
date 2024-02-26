package com.saitejajanjirala.compose_note.domain.util

import android.provider.ContactsContract.CommonDataKinds.Note

sealed class NoteOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : NoteOrder(orderType)
    class Date(orderType: OrderType) : NoteOrder(orderType)

    fun copyOrderType(orderType: OrderType) : NoteOrder{
        return when(this){
            is Date -> Date(orderType)
            is Title -> Title(orderType)
        }
    }
}
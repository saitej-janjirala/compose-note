package com.saitejajanjirala.compose_note.domain.util

sealed class OrderType {
    object ASCENDING : OrderType()
    object DESCENDING : OrderType()
}
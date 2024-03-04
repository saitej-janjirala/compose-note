package com.saitejajanjirala.compose_note.domain.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object DateUtils {
    fun getDateFromTimeInMillis(timeInMillis : Long): String{
        val sdf = SimpleDateFormat("dd MMM yyyy HH:MM", Locale.getDefault())

        val date = Date(timeInMillis)

        return sdf.format(date)
    }
}
package com.saitejajanjirala.compose_note.domain.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.room.TypeConverter
import java.nio.ByteBuffer
import kotlin.io.encoding.ExperimentalEncodingApi

class Converters {
    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun toUri(string: String?): Uri? {
        if (string == null) {
            return null
        }

        return Uri.parse(string)
    }
}
package com.saitejajanjirala.compose_note.domain.converters

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ListConverters {
    @TypeConverter
    fun fromUriListToString(value: List<Uri?>): String {
        return Gson().toJson(value.map { it?.toString() ?: "" })
    }

    @TypeConverter
    fun fromStringToUriList(value: String): List<Uri> {
        return try {
            val stringList = Gson().fromJson<List<String>>(value) // using extension function
            stringList?.map { Uri.parse(it) } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}

inline fun <reified T> Gson.fromJson(json: String?): T? {
    if (json == null) {
        return null
    }
    return this.fromJson<T>(json, object : TypeToken<T>() {}.type)
}

inline fun <reified T> Gson.toString(data: T?): String? {
    if (data == null) {
        return null
    }
    return toJson(data)
}

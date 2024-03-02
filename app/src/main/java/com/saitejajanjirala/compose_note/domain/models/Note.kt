package com.saitejajanjirala.compose_note.domain.models

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.saitejajanjirala.compose_note.domain.converters.Converters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId : Int?=0,
    val title : String,
    val description : String,
    val timeStamp : Long,
    val images : List<Uri> = emptyList()
) : Parcelable {

}

class InvalidNoteException(msg : String) : Exception(msg)
{

}
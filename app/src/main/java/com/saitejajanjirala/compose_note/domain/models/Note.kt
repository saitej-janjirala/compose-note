package com.saitejajanjirala.compose_note.domain.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId : Int?=0,
    val title : String,
    val description : String,
    val timeStamp : Long,
){

}

class InvalidNoteException(msg : String) : Exception(msg)
{

}
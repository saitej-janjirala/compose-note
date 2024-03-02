package com.saitejajanjirala.compose_note.domain.models

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(
    tableName = "images",
    foreignKeys = [ForeignKey(
        entity = Note::class,
        parentColumns = ["noteId"],
        childColumns = ["note_Id"],
        onDelete = CASCADE
    )]
)
data class ImageModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=-1,
    var note_Id: Int?=-1,
    var imageUri : Uri?
){


}

class ImageModelException(msg : String) : Exception(msg){

}
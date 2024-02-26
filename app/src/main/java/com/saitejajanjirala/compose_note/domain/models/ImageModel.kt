package com.saitejajanjirala.compose_note.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "images",
    foreignKeys = [ForeignKey(
        entity = Note::class,
        parentColumns = ["noteId"],
        childColumns = ["id"],
        onDelete = CASCADE
    )]
)
data class ImageModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=-1,
    val noteId: Int?=-1 ,
    @ColumnInfo(name = "image_data", typeAffinity = ColumnInfo.BLOB)
    val imageData : ByteArray
){

}

class ImageModelException(msg : String) : Exception(msg){

}
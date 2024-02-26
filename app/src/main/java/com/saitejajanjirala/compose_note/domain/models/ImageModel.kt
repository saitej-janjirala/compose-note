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
        childColumns = ["note_Id"],
        onDelete = CASCADE
    )]
)
data class ImageModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=-1,
    var note_Id: Int?=-1 ,
    @ColumnInfo(name = "image_data", typeAffinity = ColumnInfo.BLOB)
    val imageData : ByteArray
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageModel

        if (id != other.id) return false
        if (note_Id != other.note_Id) return false
        return imageData.contentEquals(other.imageData)
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (note_Id ?: 0)
        result = 31 * result + imageData.contentHashCode()
        return result
    }

}

class ImageModelException(msg : String) : Exception(msg){

}
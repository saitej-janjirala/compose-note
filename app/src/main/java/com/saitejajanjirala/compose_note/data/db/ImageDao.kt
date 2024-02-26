package com.saitejajanjirala.compose_note.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.saitejajanjirala.compose_note.domain.models.ImageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Insert
    fun insert(imageModel: ImageModel)

    @Delete
    fun delete(imageModel: ImageModel)

    @Query("SELECT * from images WHERE note_Id=:noteId")
    fun getAllImagesByNoteId(noteId : Int): Flow<List<ImageModel>>
}
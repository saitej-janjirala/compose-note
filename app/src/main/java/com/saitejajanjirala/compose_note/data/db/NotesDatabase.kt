package com.saitejajanjirala.compose_note.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saitejajanjirala.compose_note.domain.models.ImageModel
import com.saitejajanjirala.compose_note.domain.models.Note

@Database(entities = [Note::class, ImageModel::class], version = 1)
abstract class NotesDatabase : RoomDatabase(){
    abstract val noteDao : NoteDao
    abstract val imageDao : ImageDao
    companion object{
        const val DB_NAME="sj_compose_notes_db"
    }
}
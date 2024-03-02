package com.saitejajanjirala.compose_note.data.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.saitejajanjirala.compose_note.domain.converters.Converters
import com.saitejajanjirala.compose_note.domain.converters.ListConverters
import com.saitejajanjirala.compose_note.domain.models.ImageModel
import com.saitejajanjirala.compose_note.domain.models.Note


@Database(entities = [Note::class, ImageModel::class], version = 2)
@TypeConverters(
    Converters::class,
    ListConverters::class
)
abstract class NotesDatabase : RoomDatabase(){
    abstract val noteDao : NoteDao
    abstract val imageDao : ImageDao
    companion object{
        const val DB_NAME="sj_compose_notes_db"
    }
}
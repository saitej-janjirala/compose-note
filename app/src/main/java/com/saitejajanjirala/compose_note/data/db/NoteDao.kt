package com.saitejajanjirala.compose_note.data.db

import android.net.Uri
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.saitejajanjirala.compose_note.domain.models.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note : Note) : Long

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM notes")
    fun getAllNotes() : Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE noteId=:noteId")
    fun getNoteById(noteId : Int) : Note


}
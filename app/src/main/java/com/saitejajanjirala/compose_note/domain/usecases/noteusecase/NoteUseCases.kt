package com.saitejajanjirala.compose_note.domain.usecases.noteusecase

data class NoteUseCases(
    val addNote : AddNote,
    val deleteNote: DeleteNote,
    val getAllNotes: GetAllNotes,
    val getNoteById: GetNoteById,
    val updateNote: UpdateNote
)

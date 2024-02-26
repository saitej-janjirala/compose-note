package com.saitejajanjirala.compose_note.domain.usecases.noteusecase

import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.repository.NotesRepository
import com.saitejajanjirala.compose_note.domain.util.NoteOrder
import com.saitejajanjirala.compose_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNotes(private val notesRepository: NotesRepository) {
    operator fun invoke(noteOrder: NoteOrder): Flow<List<Note>> {
        return notesRepository.getNotes().map {notes->
            when(noteOrder){
                is NoteOrder.Date->{
                    when(noteOrder.orderType){
                        OrderType.ASCENDING->{
                            notes.sortedBy {
                                it.timeStamp
                            }
                        }
                        OrderType.DESCENDING->{
                            notes.sortedByDescending {
                                it.timeStamp
                            }
                        }
                    }
                }
                is NoteOrder.Title->{
                    when(noteOrder.orderType){
                        is OrderType.ASCENDING->{
                            notes.sortedBy {
                                it.title
                            }
                        }
                        is OrderType.DESCENDING->{
                            notes.sortedByDescending {
                                it.title
                            }
                        }
                    }
                }
            }
        }
    }
}
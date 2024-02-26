package com.saitejajanjirala.compose_note.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.compose_note.domain.usecases.noteusecase.NoteUseCases
import com.saitejajanjirala.compose_note.domain.util.NoteOrder
import com.saitejajanjirala.compose_note.domain.util.OrderType
import com.saitejajanjirala.compose_note.presentation.notes.NotesEvent
import com.saitejajanjirala.compose_note.presentation.notes.NotesState
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(val noteUseCases: NoteUseCases) : ViewModel() {
    private val _notesState = mutableStateOf(NotesState())
    val notesState : State<NotesState>
        get() = _notesState
    private var notesJob : Job? = null
    init {
        fetchNotesByOrderType(NoteOrder.Date(OrderType.DESCENDING))
    }

    private fun fetchNotesByOrderType(noteOrder: NoteOrder){
        notesJob?.cancel()
        notesJob =  noteUseCases.getAllNotes.invoke(noteOrder).onEach {
            _notesState.value = notesState.value.copy(
                notes = it,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(notesEvent: NotesEvent){
        when(notesEvent){
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    noteUseCases.deleteNote.invoke(notesEvent.note)
                }
            }
            is NotesEvent.OrderEvent -> {
                if(notesState.value::class == notesEvent.noteOrder::class
                    && notesState.value.noteOrder == notesEvent.noteOrder.orderType
                ){
                    return
                }
                fetchNotesByOrderType(notesEvent.noteOrder)
            }
            NotesEvent.RestoreNoteEvent ->{

            }
            NotesEvent.ToggleOrderSelection -> {
                _notesState.value = notesState.value.copy(
                    isOrderSectionVisible = !notesState.value.isOrderSectionVisible
                )
            }
        }
    }
}
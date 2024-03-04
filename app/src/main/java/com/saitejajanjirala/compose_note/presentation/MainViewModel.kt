package com.saitejajanjirala.compose_note.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.compose_note.domain.models.ImageModel
import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.usecases.noteusecase.NoteUseCases
import com.saitejajanjirala.compose_note.domain.util.NoteOrder
import com.saitejajanjirala.compose_note.domain.util.OrderType
import com.saitejajanjirala.compose_note.presentation.notes.NotesEvent
import com.saitejajanjirala.compose_note.presentation.notes.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _notesState = mutableStateOf(NotesState())
    val notesState : State<NotesState>
        get() = _notesState
    private var notesJob : Job? = null

    private var _eventFlow = MutableSharedFlow<ImageUiEvent>()
    val eventFlow : SharedFlow<ImageUiEvent>
        get() = _eventFlow
    init {
        fetchNotesByOrderType(NoteOrder.Date(OrderType.DESCENDING))
    }

    var recentlyDeletedNote : Note? = null

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
                    val n = notesEvent.note
                    noteUseCases.deleteNote.invoke(n)
                    recentlyDeletedNote =n
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
                if(recentlyDeletedNote!=null) {
                    viewModelScope.launch(Dispatchers.IO){
                        noteUseCases.addNote.invoke(recentlyDeletedNote!!)
                        recentlyDeletedNote = null
                    }

                }
            }
            NotesEvent.ToggleOrderSelection -> {
                _notesState.value = notesState.value.copy(
                    isOrderSectionVisible = !notesState.value.isOrderSectionVisible
                )
            }
        }
    }

    fun onNewImage(imageModel : ImageModel){
        viewModelScope.launch {
            _eventFlow.emit(
                ImageUiEvent.OnNewImage(imageModel)
            )
        }
    }
}
sealed class ImageUiEvent{
    class OnNewImage(val imageModel: ImageModel):ImageUiEvent()
}
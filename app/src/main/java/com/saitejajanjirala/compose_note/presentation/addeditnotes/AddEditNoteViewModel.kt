package com.saitejajanjirala.compose_note.presentation.addeditnotes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.compose_note.domain.models.InvalidNoteException
import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.usecases.imageusecase.ImageUseCases
import com.saitejajanjirala.compose_note.domain.usecases.noteusecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel
    @Inject constructor(
    val noteUseCases: NoteUseCases,
    val imageUseCases: ImageUseCases,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _noteTitle = mutableStateOf(
        TextFieldState(
            hint = "Enter the title here"
        )
    )
    val noteTitle : State<TextFieldState>
        get() = _noteTitle


    private var _noteDescription = mutableStateOf(
        TextFieldState(
            hint = "Enter the description here"
        )
    )

    val noteDescription : State<TextFieldState>
        get() = _noteDescription

    var currentNoteId : Int? = null

    private  val _eventFlow = MutableSharedFlow<UiEvent>()

    val eventFLow : SharedFlow<UiEvent>
        get() = _eventFlow


    private var _noteImagesState= mutableStateOf(
        NoteImagesState()
    )

    val noteImagesState : State<NoteImagesState>
        get() = _noteImagesState
    fun fetchNoteData(){
        savedStateHandle.get<Int>("note_id")?.let {noteId->
            viewModelScope.launch(Dispatchers.IO) {
                noteUseCases.getNoteById.invoke(noteId)?.also{note->
                    currentNoteId = note.noteId
                    fetchNoteImages(currentNoteId!!)
                    _noteTitle.value = noteTitle.value.copy(
                        text = note.title,
                        isHintVisible = false,
                    )
                    _noteDescription.value = noteDescription.value.copy(
                        text = note.description,
                        isHintVisible = false,
                    )
                }
            }
        }
    }

    private fun fetchNoteImages(noteId : Int){
        viewModelScope.launch (Dispatchers.IO){
           imageUseCases.getImagesByNoteId.invoke(noteId).collectLatest {
               _noteImagesState.value = noteImagesState.value.copy(
                   images = it
               )
           }

        }
    }
    fun onEvent(notesEvent: AddEditNotesEvent){
        when(notesEvent){
            is AddEditNotesEvent.OnDescriptionEntered ->{
                _noteDescription.value = noteDescription.value.copy(
                    text = notesEvent.text
                )
            }
            is AddEditNotesEvent.OnDescriptionFocusChanged -> {
                _noteDescription.value = noteDescription.value.copy(
                    isHintVisible = !notesEvent.focusState.isFocused && noteDescription.value.text.isBlank()
                )
            }
            is AddEditNotesEvent.OnTitleEntered -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = notesEvent.text
                )
            }
            is AddEditNotesEvent.OnTitleFocusChanged -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !notesEvent.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            AddEditNotesEvent.OnSaveNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try{
                        noteUseCases.addNote.invoke(Note(
                            noteId = currentNoteId,
                            title = noteTitle.value.text,
                            description = noteDescription.value.text,
                            timeStamp = System.currentTimeMillis()
                        ))
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e : InvalidNoteException){
                        _eventFlow.emit(UiEvent.ShowSnackBar(e.message.toString()))
                    }
                }
            }
        }
    }

    fun onImageEvent(imageEvent: ImageEvent){
        when(imageEvent){
            is ImageEvent.AddImage ->{
                viewModelScope.launch (Dispatchers.IO){
                    imageEvent.imageModel.note_Id = currentNoteId
                    imageUseCases.addImage.invoke(imageEvent.imageModel)
                }
            }
            is ImageEvent.DeleteImage -> {
                viewModelScope.launch (Dispatchers.IO){
                    imageUseCases.deleteImage.invoke(imageEvent.imageModel)
                }
            }
        }
    }

}

sealed class UiEvent{
    object SaveNote : UiEvent()
    data class ShowSnackBar(val msg : String): UiEvent()

}
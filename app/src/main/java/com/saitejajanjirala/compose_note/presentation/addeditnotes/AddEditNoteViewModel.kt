package com.saitejajanjirala.compose_note.presentation.addeditnotes

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.compose_note.domain.models.InvalidNoteException
import com.saitejajanjirala.compose_note.domain.models.Note
import com.saitejajanjirala.compose_note.domain.usecases.imageusecase.SaveImagesUseCase
import com.saitejajanjirala.compose_note.domain.usecases.noteusecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel
    @Inject constructor(
        private val noteUseCases: NoteUseCases,
        private val application: Application,
        private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {


    var _noteTitle = mutableStateOf(
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


    private val _noteImagesState = mutableStateListOf<Uri>()
    val noteImagesState: SnapshotStateList<Uri>
        get() = _noteImagesState

    var prevNote : Note? = null

    fun fetchNoteData(){
        savedStateHandle.get<Int>("note_id")?.let {noteId->
            viewModelScope.launch(Dispatchers.IO) {
                noteUseCases.getNoteById.invoke(noteId)?.also{note->
                    prevNote = note
                    currentNoteId = note.noteId
                    _noteImagesState.apply {
                        addAll(note.images)
                    }
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
                       val note = getCurrentNote()
                        val id = noteUseCases.addNote.invoke(note).toInt()
                        currentNoteId = id
                        prevNote = noteUseCases.getNoteById.invoke(id)
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
               viewModelScope.launch (Dispatchers.IO) {
                    val uri = SaveImagesUseCase.imageFileUri(
                        application.applicationContext,
                        imageEvent.uri,
                        System.currentTimeMillis().toInt(),
                        _noteImagesState.size
                    )
                   if (uri != null) {
                       _noteImagesState.add(uri)
                   }
               }
            }
            is ImageEvent.DeleteImage -> {
                _noteImagesState.remove(imageEvent.uri)
            }
        }
    }

    private fun getCurrentNote() : Note{
        return  Note(
            noteId = currentNoteId,
            title = noteTitle.value.text,
            images = _noteImagesState,
            description = noteDescription.value.text,
            timeStamp = System.currentTimeMillis(),
        )
    }

    fun isNoteSaved() : Boolean{
        val curr = getCurrentNote()
        val prev = prevNote
        if(prev!=null){
            return  curr.noteId==prev.noteId && compareURIs(curr.images,prev.images) &&
                    curr.title == prev.title && prev.description == curr.description
        }
        return currentNoteId != null
    }
    fun compareURIs(list1: List<Uri>, list2: List<Uri>): Boolean {

        if (list1.size != list2.size) {
            return false
        }

        // Check each URI
        for (uri in list1) {
            if (uri !in list2) {
                return false
            }
        }

        for (uri in list2) {
            if (uri !in list1) {
                return false
            }
        }

        return true
    }

}

sealed class UiEvent{
    object SaveNote : UiEvent()
    data class ShowSnackBar(val msg : String): UiEvent()
//    object DataNoteSaved : UiEvent()
}
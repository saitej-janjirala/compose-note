package com.saitejajanjirala.compose_note.presentation.addeditnotes

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
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
import java.util.Collections
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

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



    fun fetchNoteData(){
        savedStateHandle.get<Int>("note_id")?.let {noteId->
            viewModelScope.launch(Dispatchers.IO) {
                noteUseCases.getNoteById.invoke(noteId)?.also{note->
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
                        val im = noteImagesState
                        val note = Note(
                            noteId = currentNoteId,
                            title = noteTitle.value.text,
                            description = noteDescription.value.text,
                            timeStamp = System.currentTimeMillis(),
                        )
                       val id =  noteUseCases.addNote.invoke(note).toInt()
                        if(im.isNotEmpty()){

                            noteUseCases.updateNote.invoke(
                                note.copy(
                                    noteId = id,
                                    images =im,
                                    timeStamp = System.currentTimeMillis()
                                )
                            )
                        }
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



}

sealed class UiEvent{
    object SaveNote : UiEvent()
    data class ShowSnackBar(val msg : String): UiEvent()

}
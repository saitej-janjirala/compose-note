package com.saitejajanjirala.compose_note.presentation.addeditnotes.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saitejajanjirala.compose_note.R
import com.saitejajanjirala.compose_note.presentation.ImageUiEvent
import com.saitejajanjirala.compose_note.presentation.MainViewModel
import com.saitejajanjirala.compose_note.presentation.addeditnotes.AddEditNoteViewModel
import com.saitejajanjirala.compose_note.presentation.addeditnotes.AddEditNotesEvent
import com.saitejajanjirala.compose_note.presentation.addeditnotes.ImageEvent
import com.saitejajanjirala.compose_note.presentation.addeditnotes.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavHostController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
    onAttach : ()->Unit
    ) {

    val titleState = viewModel.noteTitle.value
    val descriptionState = viewModel.noteDescription.value
    val noteImagesState = viewModel.noteImagesState.value
    val scaffoldState = remember {
        SnackbarHostState()
    }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true){
        viewModel.fetchNoteData()
        viewModel.eventFLow.collect{
            focusManager.clearFocus()
            when(it){
                UiEvent.SaveNote ->{
                    navController.navigateUp()
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.showSnackbar(it.msg)
                }
            }
        }
    }

    LaunchedEffect(key1 = true){
        mainViewModel.eventFlow.collect{
            focusManager.clearFocus()
            when(it){
                is ImageUiEvent.OnNewImage ->{
                    viewModel.onImageEvent(ImageEvent.AddImage(it.imageModel))
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Your note",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    if(viewModel.currentNoteId!=null) {
                        IconButton(onClick = onAttach) {
                            Icon(
                                painterResource(id = R.drawable.ic_file_attach),
                                contentDescription = "Attach Images"
                            )
                        }
                    }
                    IconButton(onClick = {
                        viewModel.onEvent(AddEditNotesEvent.OnSaveNote)
                    }) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_save),
                            contentDescription = "Save notes"
                        )
                    }
                },
            )
        },
    ) {
        Box(
          modifier = Modifier.padding(it)
        ){
            Column {
                TransparentTextField(
                    isHintVisible = titleState.isHintVisible,
                    onFocusChange = {focusState->
                        viewModel.onEvent(AddEditNotesEvent.OnTitleFocusChanged(focusState))
                    },
                    onValueChange = {text->
                        viewModel.onEvent(AddEditNotesEvent.OnTitleEntered(text))
                    },
                    hint = titleState.hint,
                    text = titleState.text,
                    textStyle =MaterialTheme.typography.headlineSmall
                )
                TransparentTextField(
                    isHintVisible = descriptionState.isHintVisible,
                    onFocusChange = {focusState->
                        viewModel.onEvent(AddEditNotesEvent.OnDescriptionFocusChanged(focusState))
                    },
                    onValueChange = {text->
                        viewModel.onEvent(AddEditNotesEvent.OnDescriptionEntered(text))
                    },
                    hint = descriptionState.hint,
                    text = descriptionState.text,
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ){
                    items(noteImagesState.images){
                        NoteImage(onDeleteClick = {imageModel->
                            viewModel.onImageEvent(ImageEvent.DeleteImage(imageModel))
                        }, imageModel = it)
                    }
                }
            }

        }

    }





}
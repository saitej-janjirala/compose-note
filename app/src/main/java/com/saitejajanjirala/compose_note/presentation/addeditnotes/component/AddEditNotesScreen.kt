package com.saitejajanjirala.compose_note.presentation.addeditnotes.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saitejajanjirala.compose_note.R
import com.saitejajanjirala.compose_note.domain.models.ImageModel
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
    ) {



    val titleState = viewModel.noteTitle.value
    val descriptionState = viewModel.noteDescription.value
    val noteImagesState = viewModel.noteImagesState
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

//    LaunchedEffect(key1 = true){
//        mainViewModel.eventFlow.collect{
//            focusManager.clearFocus()
//            when(it){
//                is ImageUiEvent.OnNewImage ->{
//                    viewModel.onImageEvent(ImageEvent.AddImage(it.imageModel))
//                }
//            }
//        }
//    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri->
            uri?.let {
                viewModel.onImageEvent(ImageEvent.AddImage(it))

            }
        }
    )

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
                    IconButton(onClick = {
                        imageLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_file_attach),
                            contentDescription = "Attach Images"
                        )
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
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(8.dp),
                ){

                    items(noteImagesState){data->
                        NoteImage(onDeleteClick = {uri->
                            viewModel.onImageEvent(ImageEvent.DeleteImage(uri))
                        }, uri = data)
                    }

                }
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
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }


        }

    }



}


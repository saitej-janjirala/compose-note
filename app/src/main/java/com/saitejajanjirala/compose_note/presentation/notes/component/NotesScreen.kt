package com.saitejajanjirala.compose_note.presentation.notes.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saitejajanjirala.compose_note.presentation.MainViewModel
import com.saitejajanjirala.compose_note.presentation.notes.NotesEvent
import com.saitejajanjirala.compose_note.presentation.util.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavHostController,
    viewModel : MainViewModel = hiltViewModel()
) {
    val state = viewModel.notesState.value
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember{
        SnackbarHostState()
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Compose-Note")
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSelection)
                    }) {
                        Icon(
                            painterResource(id = android.R.drawable.ic_menu_sort_by_size),
                            contentDescription = "sort"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick =  {
                navController.navigate(Screen.AddEditNoteScreen.route)
            }, modifier = Modifier.background(color = Color.Transparent)){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        },

    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ){
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
            ) {
                Column (
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                ){
                    OrderSection( noteOrder = state.noteOrder, onOrderChange = {
                        viewModel.onEvent(NotesEvent.OrderEvent(it))
                    })
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()){
                items(state.notes){note->
                    NoteItem(
                        note = note,
                        onDeleteClick ={deleteNote->
                            viewModel.onEvent(NotesEvent.DeleteNote(deleteNote))
                            coroutineScope.launch {
                               val result =  snackBarHostState.showSnackbar(
                                    message = "Your note has been Deleted",
                                    actionLabel = "Undo"
                                )
                                if(result==SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(NotesEvent.RestoreNoteEvent)
                                }
                            }
                        },
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {

                                navController.navigate(Screen.AddEditNoteScreen.route + "?note_id=${note.noteId}")

                            }
                    )
                }
            }
        }
    }

}
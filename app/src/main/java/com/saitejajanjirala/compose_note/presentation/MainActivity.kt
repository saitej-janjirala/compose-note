package com.saitejajanjirala.compose_note.presentation

import android.os.Bundle
import android.transition.Slide
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saitejajanjirala.compose_note.domain.models.ImageModel
import com.saitejajanjirala.compose_note.presentation.addeditnotes.component.AddEditNoteScreen
import com.saitejajanjirala.compose_note.presentation.notes.component.NotesScreen
import com.saitejajanjirala.compose_note.presentation.util.Screen
import com.saitejajanjirala.compose_note.ui.theme.ComposenoteTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModels()
    private val imageLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri->

        uri?.let {imageUri->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val newImage = contentResolver.openInputStream(imageUri)?.readBytes()?.let {
                        ImageModel(
                            null,
                            null,
                            it
                        )
                    }
                    newImage?.let {
                        viewModel.onNewImage(it)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposenoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.NotesScreen.route){
                        composable(Screen.NotesScreen.route){
                            NotesScreen(navController)
                        }
                        composable(
                            route = Screen
                                .AddEditNoteScreen
                                .route +"?note_id={note_id}",
                            arguments = listOf(
                                navArgument(
                                    "note_id"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            ),
                        ){
                            AddEditNoteScreen(navController, mainViewModel = viewModel, onAttach = {
                                pickImages()
                            })
                        }
                    }
                }
            }
        }
    }

    private fun pickImages(){
        imageLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
}

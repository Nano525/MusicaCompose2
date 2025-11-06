package mx.edu.utez.musicacompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mx.edu.utez.musicacompose.data.model.AlbumConCanciones
import mx.edu.utez.musicacompose.ui.components.buttons.PrimaryButton
import mx.edu.utez.musicacompose.ui.components.text.Title
import mx.edu.utez.musicacompose.ui.components.list.AlbumList
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel

@Composable
fun HomeScreen(viewModel: AlbumViewModel, navController: NavController) {
    val albums by viewModel.Albums.collectAsStateWithLifecycle()
    
    Column(modifier = Modifier.padding(16.dp)) {
        Title("Álbumes registrados")
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton("Agregar álbum") {
            viewModel.agregar(navController)
        }
        Spacer(modifier = Modifier.height(16.dp))
        AlbumList(albums) { albumConCanciones ->
            viewModel.clickAlbum(albumConCanciones)
            navController.navigate("cancion")
        }
    }
}
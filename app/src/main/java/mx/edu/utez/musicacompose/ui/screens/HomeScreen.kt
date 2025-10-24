package mx.edu.utez.musicacompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.ui.components.buttons.PrimaryButton
import mx.edu.utez.musicacompose.ui.components.text.Label
import mx.edu.utez.musicacompose.ui.components.text.Title
import mx.edu.utez.musicacompose.ui.components.list.AlbumList
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel

@Composable
fun HomeScreen(viewModel: AlbumViewModel, navController: NavController){
    Column{
        Title("Album registradas")
        PrimaryButton("Agregar album") {
            viewModel.agregar(navController)
        }
        val albums by viewModel.Albums.collectAsStateWithLifecycle()
        AlbumList(albums) { album ->
            viewModel.clickAlbum(album)
            navController.navigate("cancion")
        }
    }
}
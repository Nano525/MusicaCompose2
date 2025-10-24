package mx.edu.utez.musicacompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.ui.components.text.Label
import mx.edu.utez.musicacompose.ui.components.text.Title
import mx.edu.utez.musicacompose.ui.components.list.AlbumList
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel

@Composable
fun HomeScreen(viewModel: AlbumViewModel, navController: NavController){
    Column{
        Title("Album registradas")
        val Album by viewModel.Album.collectAsStateWithLifecycle()
        AlbumList(Album) { passport ->
            viewModel.clickAlbum(passport)
            navController.navigate("cancion")
        }
        Label("Fin de la lista")
    }
}
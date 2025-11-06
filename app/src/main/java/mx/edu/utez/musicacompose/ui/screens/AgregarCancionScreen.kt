package mx.edu.utez.musicacompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mx.edu.utez.musicacompose.R
import mx.edu.utez.musicacompose.data.model.Cancion
import mx.edu.utez.musicacompose.ui.components.buttons.PrimaryButton
import mx.edu.utez.musicacompose.ui.components.image.CircularImage
import mx.edu.utez.musicacompose.ui.components.inputs.UserInputField
import mx.edu.utez.musicacompose.ui.components.text.Title
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel

@Composable
fun AgregarCancionScreen(viewModel: AlbumViewModel, navController: NavController) {
    val selectedAlbum by viewModel.selectedAlbum.collectAsStateWithLifecycle()
    
    val nombre = remember { mutableStateOf("") }
    val artista = remember { mutableStateOf("") }
    val duracion = remember { mutableStateOf("") }
    val genero = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        CircularImage(R.drawable.logoapp)
        Title("Agregar canción")

        UserInputField(
            value = nombre,
            label = "Nombre de la canción"
        )

        UserInputField(
            value = artista,
            label = "Artista"
        )

        UserInputField(
            value = duracion,
            label = "Duración (ej: 4:31)"
        )

        UserInputField(
            value = genero,
            label = "Género"
        )

        PrimaryButton("Agregar") {
            selectedAlbum?.let { albumConCanciones ->
                if (nombre.value.isNotBlank() && artista.value.isNotBlank() && 
                    duracion.value.isNotBlank() && genero.value.isNotBlank()) {
                    val nuevaCancion = Cancion(
                        nombre = nombre.value,
                        artista = artista.value,
                        duracion = duracion.value,
                        genero = genero.value,
                        albumId = albumConCanciones.album.id
                    )
                    viewModel.insertCancion(nuevaCancion)
                    viewModel.agregarCancionSalir(navController)
                }
            }
        }
    }
}


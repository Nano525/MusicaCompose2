package mx.edu.utez.musicacompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.musicacompose.R
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.ui.components.buttons.PrimaryButton
import mx.edu.utez.musicacompose.ui.components.image.CircularImage
import mx.edu.utez.musicacompose.ui.components.inputs.UserInputField
import mx.edu.utez.musicacompose.ui.components.text.Title
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel

@Composable
fun AgregarScreen(viewModel: AlbumViewModel, navController: NavController) {
    val nombre = remember { mutableStateOf("") }
    val artista = remember { mutableStateOf("") }
    // Por ahora usamos una imagen por defecto, puedes agregar un selector después
    val imagen = R.drawable.logoapp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        CircularImage(R.drawable.logoapp)
        Title("Agregar album")

        UserInputField(
            value = nombre,
            label = "Nombre del álbum"
        )

        UserInputField(
            value = artista,
            label = "Artista"
        )

        PrimaryButton("Agregar") {
            if (nombre.value.isNotBlank() && artista.value.isNotBlank()) {
                val nuevoAlbum = Album(
                    nombre = nombre.value,
                    artista = artista.value,
                    imagen = imagen
                )
                viewModel.insertAlbum(nuevoAlbum)
                viewModel.agregarSalir(navController)
            }
        }
    }
}
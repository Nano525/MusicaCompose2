package mx.edu.utez.musicacompose.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mx.edu.utez.musicacompose.R
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.ui.components.buttons.PrimaryButton
import mx.edu.utez.musicacompose.ui.components.image.CircularImage
import mx.edu.utez.musicacompose.ui.components.inputs.UserInputField
import mx.edu.utez.musicacompose.ui.components.text.Title
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun AgregarScreen(viewModel: AlbumViewModel, navController: NavController) {
    val nombre = remember { mutableStateOf("") }
    val artista = remember { mutableStateOf("") }
    val imagenUri = remember { mutableStateOf<Uri?>(null) }
    val imagenFile = remember { mutableStateOf<File?>(null) }
    val context = LocalContext.current

    // Launcher para seleccionar imagen de la galería
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imagenUri.value = it
            // Convertir Uri a File
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                val file = File(context.cacheDir, "album_image_${System.currentTimeMillis()}.jpg")
                val outputStream = FileOutputStream(file)
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                imagenFile.value = file
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        // Mostrar imagen seleccionada o imagen por defecto
        if (imagenUri.value != null) {
            AsyncImage(
                model = imagenUri.value,
                contentDescription = "Imagen del álbum",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        } else {
            CircularImage(R.drawable.logoapp)
        }

        // Botón para seleccionar imagen
        PrimaryButton("Seleccionar imagen") {
            imagePickerLauncher.launch("image/*")
        }

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
                // Llamar a la función de API con la imagen seleccionada
                viewModel.createAlbumOnApi(
                    nombre = nombre.value,
                    artista = artista.value,
                    imagenFile = imagenFile.value
                )
                viewModel.agregarSalir(navController)
            }
        }
    }
}
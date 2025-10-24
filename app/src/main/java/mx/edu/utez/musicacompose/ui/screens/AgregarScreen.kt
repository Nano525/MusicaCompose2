package mx.edu.utez.musicacompose.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.musicacompose.R
import mx.edu.utez.musicacompose.ui.components.buttons.PrimaryButton
import mx.edu.utez.musicacompose.ui.components.image.CircularImage
import mx.edu.utez.musicacompose.ui.components.inputs.NumberInputField
import mx.edu.utez.musicacompose.ui.components.inputs.UserInputField
import mx.edu.utez.musicacompose.ui.components.text.Title
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun AgregarScreen(viewModel: AlbumViewModel, navController: NavController){
    var texto = mutableStateOf("")
    Column (
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ){
        CircularImage(R.drawable.logoapp)
        Title("Agregar album")

        NumberInputField(
            "Nombre"
            ,texto
        )
        NumberInputField(
            "Artista",
            texto
        )
        NumberInputField(
            "Cancion",
            texto
        )
        PrimaryButton("Agregar") {
            viewModel.agregarSalir(navController)
        }



    }
}
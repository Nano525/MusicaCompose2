package mx.edu.utez.musicacompose.data.model

import androidx.annotation.DrawableRes

data class Cancion(
    val id: Int,
    val nombre: String,
    val artista: String,
    val duracion: String,
    val genero: String,
    @DrawableRes val imagen: Int

)
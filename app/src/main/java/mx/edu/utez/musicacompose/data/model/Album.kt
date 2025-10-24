package mx.edu.utez.musicacompose.data.model

import androidx.annotation.DrawableRes


data class Album {
    val id: Int,
    val nombre: String,
    val artista: String,
    @DrawableRes val imagen: Int
}
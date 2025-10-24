package mx.edu.utez.musicacompose.data.model



import androidx.annotation.DrawableRes


data class Album (
    val id: Int,
    val nombre: String,
    val artista: String,
    val canciones: List<Cancion>,
    @DrawableRes val imagen: Int

    )


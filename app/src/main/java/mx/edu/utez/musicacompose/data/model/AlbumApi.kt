package mx.edu.utez.musicacompose.data.model

import com.google.gson.annotations.SerializedName

// Modelo DTO para la API (sin anotaciones de Room)
data class AlbumApi(
    val id: Int = 0,
    val nombre: String,
    val artista: String,
    val imagen: String  // String en lugar de Int para la API
)

// Modelo DTO para la respuesta de la API
data class AlbumConCancionesApi(
    @SerializedName("album")
    val album: AlbumApi,
    @SerializedName("canciones")
    val canciones: List<Cancion>
)


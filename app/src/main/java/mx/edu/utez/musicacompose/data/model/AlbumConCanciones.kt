package mx.edu.utez.musicacompose.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class AlbumConCanciones(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val canciones: List<Cancion>
)

package mx.edu.utez.musicacompose.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "canciones",
    foreignKeys = [
        ForeignKey(
            entity = Album::class,
            parentColumns = ["id"],
            childColumns = ["albumId"],
            onDelete = ForeignKey.CASCADE // si se elimina un álbum, sus canciones también
        )
    ]
)
data class Cancion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val artista: String,
    val duracion: String,
    val genero: String,
    val albumId: Int // Clave foránea al álbum
)

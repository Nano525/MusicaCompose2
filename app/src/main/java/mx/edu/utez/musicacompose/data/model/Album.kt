package mx.edu.utez.musicacompose.data.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val artista: String,
    @DrawableRes val imagen: Int
)

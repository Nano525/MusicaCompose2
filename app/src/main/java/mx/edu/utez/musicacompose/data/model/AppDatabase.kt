package mx.edu.utez.musicacompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.data.model.AlbumDao
import mx.edu.utez.musicacompose.data.model.Cancion

@Database(entities = [Album::class, Cancion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}

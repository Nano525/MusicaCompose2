package mx.edu.utez.musicacompose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.data.model.AlbumDao
import mx.edu.utez.musicacompose.data.model.Cancion

@Database(entities = [Album::class, Cancion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "musica_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

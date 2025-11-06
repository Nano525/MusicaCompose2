package mx.edu.utez.musicacompose.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    // Inserta un nuevo álbum
    @Insert
    suspend fun insertAlbum(album: Album)

    // Inserta una nueva canción
    @Insert
    suspend fun insertCancion(cancion: Cancion)

    // Obtiene todos los álbumes con sus canciones (ordenados por nombre)
    @Transaction
    @Query("SELECT * FROM albums ORDER BY nombre ASC")
    fun getAllAlbums(): Flow<List<AlbumConCanciones>>

    // Obtiene un álbum específico por su ID con todas sus canciones
    @Transaction
    @Query("SELECT * FROM albums WHERE id = :albumId")
    suspend fun getAlbumById(albumId: Int): AlbumConCanciones?
}
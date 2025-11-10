package mx.edu.utez.musicacompose.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    // ========== ÁLBUMES ==========
    
    // Inserta un nuevo álbum y retorna el ID generado
    @Insert
    suspend fun insertAlbum(album: Album): Long

    // Actualiza un álbum
    @Update
    suspend fun updateAlbum(album: Album)

    // Elimina un álbum
    @Query("DELETE FROM albums WHERE id = :albumId")
    suspend fun deleteAlbum(albumId: Int)

    // Elimina todos los álbumes (las canciones se eliminan automáticamente por CASCADE)
    @Query("DELETE FROM albums")
    suspend fun deleteAllAlbums()

    // Inserta múltiples álbumes en una transacción
    @Transaction
    suspend fun replaceAllAlbums(albums: List<Album>, canciones: List<Cancion>) {
        deleteAllAlbums()
        albums.forEach { insertAlbum(it) }
        canciones.forEach { insertCancion(it) }
    }

    // Obtiene todos los álbumes con sus canciones (ordenados por nombre)
    @Transaction
    @Query("SELECT * FROM albums ORDER BY nombre ASC")
    fun getAllAlbums(): Flow<List<AlbumConCanciones>>

    // Obtiene un álbum específico por su ID con todas sus canciones
    @Transaction
    @Query("SELECT * FROM albums WHERE id = :albumId")
    suspend fun getAlbumById(albumId: Int): AlbumConCanciones?

    // Obtiene un álbum simple por ID (sin canciones)
    @Query("SELECT * FROM albums WHERE id = :albumId")
    suspend fun getAlbumByIdSimple(albumId: Int): Album?

    // ========== CANCIONES ==========

    // Inserta una nueva canción
    @Insert
    suspend fun insertCancion(cancion: Cancion)

    // Actualiza una canción
    @Update
    suspend fun updateCancion(cancion: Cancion)

    // Elimina una canción
    @Query("DELETE FROM canciones WHERE id = :cancionId")
    suspend fun deleteCancion(cancionId: Int)

    // Obtiene todas las canciones de un álbum
    @Query("SELECT * FROM canciones WHERE albumId = :albumId ORDER BY nombre ASC")
    suspend fun getCancionesByAlbumId(albumId: Int): List<Cancion>

    // Obtiene una canción por ID
    @Query("SELECT * FROM canciones WHERE id = :cancionId")
    suspend fun getCancionById(cancionId: Int): Cancion?
}
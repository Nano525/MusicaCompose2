package mx.edu.utez.musicacompose.data.repository

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import mx.edu.utez.musicacompose.R
import mx.edu.utez.musicacompose.data.Api.ApiClient
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.data.model.AlbumApi
import mx.edu.utez.musicacompose.data.model.AlbumConCanciones
import mx.edu.utez.musicacompose.data.model.AlbumConCancionesApi
import mx.edu.utez.musicacompose.data.model.AlbumDao
import mx.edu.utez.musicacompose.data.model.Cancion
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AlbumRepository(
    private val albumDao: AlbumDao,
    private val database: mx.edu.utez.musicacompose.data.AppDatabase
) {
    private val apiService = ApiClient.instance

    val allAlbums: Flow<List<AlbumConCanciones>> = albumDao.getAllAlbums()

    // ========== ÁLBUMES ==========
    
    suspend fun insertAlbum(album: Album): Long {
        return albumDao.insertAlbum(album)
    }

    suspend fun updateAlbum(album: Album) {
        albumDao.updateAlbum(album)
    }

    suspend fun deleteAlbum(albumId: Int) {
        albumDao.deleteAlbum(albumId)
    }

    suspend fun getAlbumById(albumId: Int): AlbumConCanciones? {
        return albumDao.getAlbumById(albumId)
    }

    suspend fun getAlbumByIdSimple(albumId: Int): Album? {
        return albumDao.getAlbumByIdSimple(albumId)
    }

    // ========== CANCIONES ==========

    suspend fun insertCancion(cancion: Cancion) {
        albumDao.insertCancion(cancion)
    }

    suspend fun updateCancion(cancion: Cancion) {
        albumDao.updateCancion(cancion)
    }

    suspend fun deleteCancion(cancionId: Int) {
        albumDao.deleteCancion(cancionId)
    }

    suspend fun getCancionesByAlbumId(albumId: Int): List<Cancion> {
        return albumDao.getCancionesByAlbumId(albumId)
    }

    suspend fun getCancionById(cancionId: Int): Cancion? {
        return albumDao.getCancionById(cancionId)
    }

    // ========== LIMPIEZA ==========

    suspend fun clearAllAlbums() {
        albumDao.deleteAllAlbums()
    }

    suspend fun replaceAllAlbums(
        albums: List<Album>,
        cancionesByAlbum: Map<Int, List<Cancion>>
    ) {
        database.withTransaction {
            albumDao.deleteAllAlbums()
            // Insertar álbumes y obtener sus IDs
            albums.forEachIndexed { index, album ->
                val albumId = albumDao.insertAlbum(album).toInt()
                // Insertar canciones de este álbum con el ID correcto
                cancionesByAlbum[index]?.forEach { cancion ->
                    albumDao.insertCancion(cancion.copy(albumId = albumId))
                }
            }
        }
    }

    // ========== FUNCIONES DE API (READ) ==========

    suspend fun getAlbumsFromApi(): List<AlbumConCanciones> {
        return try {
            val response = apiService.getAlbums()
            if (response.isSuccessful) {
                val albumsApi = response.body() ?: emptyList()
                // Convertir de AlbumConCancionesApi a AlbumConCanciones (sin insertar en Room todavía)
                albumsApi.map { albumApiDto ->
                    // Convertir AlbumApi a Album con URL de imagen
                    // Construir URL completa de la imagen
                    val imagenUrl = if (!albumApiDto.album.imagen.isNullOrBlank()) {
                        // Si la imagen ya es una URL completa, usarla directamente
                        if (albumApiDto.album.imagen.startsWith("http")) {
                            albumApiDto.album.imagen
                        } else {
                            // Construir URL completa desde la ruta relativa
                            // El servidor retorna /uploads/filename.jpg, necesitamos agregar el base URL
                            val baseUrl = "http://192.168.0.123:5000"
                            if (albumApiDto.album.imagen.startsWith("/")) {
                                "$baseUrl${albumApiDto.album.imagen}"
                            } else {
                                "$baseUrl/${albumApiDto.album.imagen}"
                            }
                        }
                    } else {
                        null
                    }
                    
                    // Crear Album sin insertar todavía
                    val album = Album(
                        id = 0, // Room generará el ID automáticamente
                        nombre = albumApiDto.album.nombre,
                        artista = albumApiDto.album.artista,
                        imagen = 0, // No usar drawable cuando hay URL
                        imagenUrl = imagenUrl
                    )
                    
                    // Retornar AlbumConCanciones sin insertar en Room
                    AlbumConCanciones(
                        album = album,
                        canciones = albumApiDto.canciones
                    )
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // ========== FUNCIONES DE API (CREATE) ==========

    suspend fun createAlbumOnApi(
        nombre: String,
        artista: String,
        imagenFile: File? = null
    ) {
        try {
            // 1. Convertir los Strings a RequestBody
            val nombreBody = nombre.toRequestBody("text/plain".toMediaTypeOrNull())
            val artistaBody = artista.toRequestBody("text/plain".toMediaTypeOrNull())

            // 2. Convertir el File de la imagen a MultipartBody.Part
            var imagenPart: MultipartBody.Part? = null
            if (imagenFile != null && imagenFile.exists()) {
                val requestFile = imagenFile.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
                // 'imagen' debe coincidir con el nombre en el backend (request.files.get('imagen'))
                imagenPart = MultipartBody.Part.createFormData("imagen", imagenFile.name, requestFile)
            }

            // 3. Llamar a la API
            apiService.postAlbum(
                nombre = nombreBody,
                artista = artistaBody,
                imagen = imagenPart,
                canciones = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
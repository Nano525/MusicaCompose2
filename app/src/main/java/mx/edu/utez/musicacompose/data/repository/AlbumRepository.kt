package mx.edu.utez.musicacompose.data.repository

import kotlinx.coroutines.flow.Flow
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.data.model.AlbumConCanciones
import mx.edu.utez.musicacompose.data.model.AlbumDao
import mx.edu.utez.musicacompose.data.model.Cancion

class AlbumRepository(private val albumDao: AlbumDao) {

    val allAlbums: Flow<List<AlbumConCanciones>> = albumDao.getAllAlbums()

    // ========== ÁLBUMES ==========
    
    suspend fun insertAlbum(album: Album) {
        albumDao.insertAlbum(album)
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
}
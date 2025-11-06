package mx.edu.utez.musicacompose.data.repository

import kotlinx.coroutines.flow.Flow
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.data.model.AlbumConCanciones
import mx.edu.utez.musicacompose.data.model.AlbumDao
import mx.edu.utez.musicacompose.data.model.Cancion

class AlbumRepository(private val albumDao: AlbumDao) {

    val allAlbums: Flow<List<AlbumConCanciones>> = albumDao.getAllAlbums()

    suspend fun insertAlbum(album: Album) {
        albumDao.insertAlbum(album)
    }
    suspend fun insertCancion(cancion: Cancion) {
        albumDao.insertCancion(cancion)
    }
}
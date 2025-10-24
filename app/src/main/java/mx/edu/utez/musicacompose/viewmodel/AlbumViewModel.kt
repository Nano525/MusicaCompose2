package mx.edu.utez.musicacompose.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.edu.utez.musicacompose.data.model.Album


class AlbumViewModel: ViewModel() {

    val _Albums = MutableStateFlow<List<Album>>(emptyList())
    val Albums: StateFlow<List<Album>> = _Albums
    private val _selectedAlbum = MutableStateFlow<Album?>(null)
    val selectedAlbum: StateFlow<Album?> = _selectedAlbum

    init {
        _Albums.value = listOf(
            //Pasaportes
        )
    }
    fun clickAlbum(Album: Album){
        println("Has hecho click en: ${Album.nombre}")
        _selectedAlbum.value = Album
    }

}
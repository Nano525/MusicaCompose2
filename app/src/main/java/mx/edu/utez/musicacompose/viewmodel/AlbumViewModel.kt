package mx.edu.utez.musicacompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import mx.edu.utez.musicacompose.R
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.data.model.Cancion

class AlbumViewModel : ViewModel() {
    // Lista de álbumes
    val albums = mutableStateOf(
        listOf(
            Album(
                id = 1,
                nombre = "Appetite for Destruction",
                artista = "Guns N' Roses",
                imagen = R.drawable.albumappetite
            ),
            Album(
                id = 2,
                nombre = "Nevermind",
                artista = "Nirvana",
                imagen = R.drawable.albumnevermind
            ),
            Album(
                id = 3,
                nombre = "Disconnected",
                artista = "Fates Warning",
                imagen = R.drawable.albumdisconected
            )
        )
    )
    
    // Álbum seleccionado
    val selectedAlbum = mutableStateOf<Album?>(null)
    
    // Lista de canciones del álbum seleccionado
    val albumSongs = mutableStateOf<List<Cancion>>(emptyList())
    
    // Estado de carga
    val isLoading = mutableStateOf(false)
    
    // Error
    val error = mutableStateOf("")
    
    fun selectAlbum(album: Album) {
        selectedAlbum.value = album
        loadAlbumSongs(album.id)
    }
    
    private fun loadAlbumSongs(albumId: Int) {
        isLoading.value = true
        error.value = ""
        
        // Simulamos carga de canciones basado en el álbum
        albumSongs.value = when (albumId) {
            1 -> listOf(
                Cancion(1, "Welcome to the Jungle", "Guns N' Roses", "4:33", "Hard Rock", R.drawable.albumappetite),
                Cancion(2, "Sweet Child O' Mine", "Guns N' Roses", "5:56", "Hard Rock", R.drawable.albumappetite),
                Cancion(3, "Paradise City", "Guns N' Roses", "6:46", "Hard Rock", R.drawable.albumappetite)
            )
            2 -> listOf(
                Cancion(4, "Smells Like Teen Spirit", "Nirvana", "5:01", "Grunge", R.drawable.albumnevermind),
                Cancion(5, "Come as You Are", "Nirvana", "3:38", "Grunge", R.drawable.albumnevermind),
                Cancion(6, "Lithium", "Nirvana", "4:16", "Grunge", R.drawable.albumnevermind)
            )
            3 -> listOf(
                Cancion(7, "Disconnected", "Fates Warning", "5:20", "Progressive Metal", R.drawable.albumdisconected),
                Cancion(8, "Still Remains", "Fates Warning", "4:23", "Progressive Metal", R.drawable.albumdisconected),
                Cancion(9, "A Pleasant Shade of Gray", "Fates Warning", "6:15", "Progressive Metal", R.drawable.albumdisconected)
            )
            else -> emptyList()
        }
        
        isLoading.value = false
    }
    
    fun clearSelection() {
        selectedAlbum.value = null
        albumSongs.value = emptyList()
    }
}
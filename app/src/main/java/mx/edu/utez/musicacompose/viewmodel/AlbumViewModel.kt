package mx.edu.utez.musicacompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.data.model.Cancion
import mx.edu.utez.musicacompose.R


class AlbumViewModel: ViewModel() {

    val _Albums = MutableStateFlow<List<Album>>(emptyList())
    val Albums: StateFlow<List<Album>> = _Albums
    private val _selectedAlbum = MutableStateFlow<Album?>(null)
    val selectedAlbum: StateFlow<Album?> = _selectedAlbum

    init {
        _Albums.value = listOf(
            Album(
                id = 1,
                nombre = "Appetite for Destruction",
                artista = "Guns N' Roses",
                canciones = listOf(
                    Cancion(1, "Welcome to the Jungle", "Guns N' Roses", "4:31", "Hard Rock"),
                    Cancion(2, "It's So Easy", "Guns N' Roses", "3:22", "Hard Rock"),
                    Cancion(3, "Nightrain", "Guns N' Roses", "4:28", "Hard Rock")
                ),
                imagen = R.drawable.albumappetite
            ),
            Album(
                id = 2,
                nombre = "Back in Black",
                artista = "AC/DC",
                canciones = listOf(
                    Cancion(4, "Hells Bells", "AC/DC", "5:12", "Hard Rock"),
                    Cancion(5, "Shoot to Thrill", "AC/DC", "5:17", "Hard Rock"),
                    Cancion(6, "Back in Black", "AC/DC", "4:15", "Hard Rock")
                ),
                imagen = R.drawable.ac
            ),
            Album(
                id = 3,
                nombre = "The Dark Side of the Moon",
                artista = "Pink Floyd",
                canciones = listOf(
                    Cancion(7, "Speak to Me", "Pink Floyd", "1:30", "Progressive Rock"),
                    Cancion(8, "Breathe (In the Air)", "Pink Floyd", "2:43", "Progressive Rock"),
                    Cancion(9, "Time", "Pink Floyd", "6:53", "Progressive Rock")
                ),
                imagen = R.drawable.triagulo
            ),
            Album(
                id = 4,
                nombre = "Get Jinxed",
                artista = "League of Legends",
                canciones = listOf(
                    Cancion(10, "Get Jinxed", "Jinx", "3:22", "Pop Rock"),
                    Cancion(11, "Legends Never Die", "Against The Current", "3:55", "Epic Rock"),
                    Cancion(12, "Warriors", "Imagine Dragons", "2:50", "Alternative Rock")
                ),
                imagen = R.drawable.loca
            ),
            Album(
                id = 5,
                nombre = "Bratva",
                artista = "Vladimir",
                canciones = listOf(
                    Cancion(13, "Brotherhood", "Vladimir", "3:45", "Dark Trap"),
                    Cancion(14, "Cold Streets", "Vladimir", "4:10", "Dark Trap"),
                    Cancion(15, "No Mercy", "Vladimir", "3:58", "Dark Trap")
                ),
                imagen = R.drawable.loco
            )
        )
    }
    fun clickAlbum(Album: Album){
        println("Has hecho click en: ${Album.nombre}")
        _selectedAlbum.value = Album
    }

    fun agregar(navController: NavController) {
        navController.navigate("agregar") {
            popUpTo("agregar") { inclusive = true }
        }
    }

    fun editar(navController: NavController) {
        navController.navigate("editar") {
            popUpTo("editar") { inclusive = true }
        }
    }
    fun eliminar(navController: NavController) {
        navController.navigate("home") {
            popUpTo("home") { inclusive = true }
        }
    }

    fun agregarSalir(navController: NavController) {
        navController.navigate("home") {
            popUpTo("home") { inclusive = true }
        }
    }

    fun editarSalir(navController: NavController) {
        navController.navigate("cancion") {
            popUpTo("cancion") { inclusive = true }
        }
    }
}
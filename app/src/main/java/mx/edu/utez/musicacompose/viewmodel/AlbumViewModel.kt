package mx.edu.utez.musicacompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mx.edu.utez.musicacompose.data.model.Album
import mx.edu.utez.musicacompose.data.model.AlbumConCanciones
import mx.edu.utez.musicacompose.data.model.Cancion
import mx.edu.utez.musicacompose.R
import mx.edu.utez.musicacompose.data.repository.AlbumRepository

class AlbumViewModel(private val albumRepository: AlbumRepository) : ViewModel() {

    private val _Albums = MutableStateFlow<List<AlbumConCanciones>>(emptyList())
    val Albums: StateFlow<List<AlbumConCanciones>> = _Albums.asStateFlow()

    private val _selectedAlbum = MutableStateFlow<AlbumConCanciones?>(null)
    val selectedAlbum: StateFlow<AlbumConCanciones?> = _selectedAlbum.asStateFlow()

    private val _selectedCancion = MutableStateFlow<Cancion?>(null)
    val selectedCancion: StateFlow<Cancion?> = _selectedCancion.asStateFlow()

    init {
        // Observar cambios en la base de datos
        albumRepository.allAlbums
            .onEach { albums ->
                _Albums.value = albums
                // Si no hay álbumes, inicializar con datos de ejemplo
                if (albums.isEmpty()) {
                    initializeDatabase()
                }
            }
            .launchIn(viewModelScope)
    }

    // ========== ÁLBUMES ==========

    fun clickAlbum(album: AlbumConCanciones) {
        _selectedAlbum.value = album
    }

    fun insertAlbum(album: Album) {
        viewModelScope.launch {
            albumRepository.insertAlbum(album)
        }
    }

    fun updateAlbum(album: Album) {
        viewModelScope.launch {
            albumRepository.updateAlbum(album)
            // Recargar el álbum seleccionado
            album.id.let { albumId ->
                albumRepository.getAlbumById(albumId)?.let { updatedAlbum ->
                    _selectedAlbum.value = updatedAlbum
                }
            }
        }
    }

    fun deleteAlbum(albumId: Int) {
        viewModelScope.launch {
            albumRepository.deleteAlbum(albumId)
            // Limpiar selección si se eliminó el álbum seleccionado
            if (_selectedAlbum.value?.album?.id == albumId) {
                _selectedAlbum.value = null
            }
        }
    }

    fun loadAlbumById(albumId: Int) {
        viewModelScope.launch {
            albumRepository.getAlbumById(albumId)?.let { album ->
                _selectedAlbum.value = album
            }
        }
    }

    // ========== CANCIONES ==========

    fun selectCancion(cancion: Cancion) {
        _selectedCancion.value = cancion
    }

    fun insertCancion(cancion: Cancion) {
        viewModelScope.launch {
            albumRepository.insertCancion(cancion)
            // Recargar el álbum seleccionado para actualizar la lista de canciones
            _selectedAlbum.value?.album?.id?.let { albumId ->
                albumRepository.getAlbumById(albumId)?.let { updatedAlbum ->
                    _selectedAlbum.value = updatedAlbum
                }
            }
        }
    }

    fun updateCancion(cancion: Cancion) {
        viewModelScope.launch {
            albumRepository.updateCancion(cancion)
            // Recargar el álbum seleccionado para actualizar la lista de canciones
            _selectedAlbum.value?.album?.id?.let { albumId ->
                albumRepository.getAlbumById(albumId)?.let { updatedAlbum ->
                    _selectedAlbum.value = updatedAlbum
                }
            }
        }
    }

    fun deleteCancion(cancionId: Int) {
        viewModelScope.launch {
            albumRepository.deleteCancion(cancionId)
            // Limpiar selección si se eliminó la canción seleccionada
            if (_selectedCancion.value?.id == cancionId) {
                _selectedCancion.value = null
            }
            // Recargar el álbum seleccionado para actualizar la lista de canciones
            _selectedAlbum.value?.album?.id?.let { albumId ->
                albumRepository.getAlbumById(albumId)?.let { updatedAlbum ->
                    _selectedAlbum.value = updatedAlbum
                }
            }
        }
    }

    // ========== NAVEGACIÓN ==========

    fun agregar(navController: NavController) {
        navController.navigate("agregar")
    }

    fun editar(navController: NavController) {
        navController.navigate("editar")
    }

    fun eliminar(navController: NavController) {
        navController.navigate("home") {
            popUpTo("home") { inclusive = false }
        }
    }

    fun agregarSalir(navController: NavController) {
        navController.navigate("home") {
            popUpTo("agregar") { inclusive = true }
        }
    }

    fun editarSalir(navController: NavController) {
        navController.navigate("cancion") {
            popUpTo("editar") { inclusive = true }
        }
    }

    fun agregarCancion(navController: NavController) {
        navController.navigate("agregar_cancion")
    }

    fun editarCancion(navController: NavController) {
        navController.navigate("editar_cancion")
    }

    fun agregarCancionSalir(navController: NavController) {
        navController.navigate("cancion") {
            popUpTo("agregar_cancion") { inclusive = true }
        }
    }

    fun editarCancionSalir(navController: NavController) {
        navController.navigate("cancion") {
            popUpTo("editar_cancion") { inclusive = true }
        }
    }

    // ========== INICIALIZACIÓN DE DATOS ==========

    private fun initializeDatabase() {
        viewModelScope.launch {
            // Insertar álbumes
            val album1 = Album(
                nombre = "Appetite for Destruction",
                artista = "Guns N' Roses",
                imagen = R.drawable.albumappetite
            )
            val album2 = Album(
                nombre = "Back in Black",
                artista = "AC/DC",
                imagen = R.drawable.ac
            )
            val album3 = Album(
                nombre = "The Dark Side of the Moon",
                artista = "Pink Floyd",
                imagen = R.drawable.triagulo
            )
            val album4 = Album(
                nombre = "Get Jinxed",
                artista = "League of Legends",
                imagen = R.drawable.loca
            )
            val album5 = Album(
                nombre = "Bratva",
                artista = "Vladimir",
                imagen = R.drawable.loco
            )

            albumRepository.insertAlbum(album1)
            albumRepository.insertAlbum(album2)
            albumRepository.insertAlbum(album3)
            albumRepository.insertAlbum(album4)
            albumRepository.insertAlbum(album5)

            // Esperar un momento para que se inserten los álbumes
            kotlinx.coroutines.delay(200)

            // Obtener los álbumes insertados
            albumRepository.allAlbums.first().let { albumList ->
                albumList.forEach { albumConCanciones ->
                    val album = albumConCanciones.album
                    val canciones = when (album.nombre) {
                        "Appetite for Destruction" -> listOf(
                            Cancion(nombre = "Welcome to the Jungle", artista = "Guns N' Roses", duracion = "4:31", genero = "Hard Rock", albumId = album.id),
                            Cancion(nombre = "It's So Easy", artista = "Guns N' Roses", duracion = "3:22", genero = "Hard Rock", albumId = album.id),
                            Cancion(nombre = "Nightrain", artista = "Guns N' Roses", duracion = "4:28", genero = "Hard Rock", albumId = album.id)
                        )
                        "Back in Black" -> listOf(
                            Cancion(nombre = "Hells Bells", artista = "AC/DC", duracion = "5:12", genero = "Hard Rock", albumId = album.id),
                            Cancion(nombre = "Shoot to Thrill", artista = "AC/DC", duracion = "5:17", genero = "Hard Rock", albumId = album.id),
                            Cancion(nombre = "Back in Black", artista = "AC/DC", duracion = "4:15", genero = "Hard Rock", albumId = album.id)
                        )
                        "The Dark Side of the Moon" -> listOf(
                            Cancion(nombre = "Speak to Me", artista = "Pink Floyd", duracion = "1:30", genero = "Progressive Rock", albumId = album.id),
                            Cancion(nombre = "Breathe (In the Air)", artista = "Pink Floyd", duracion = "2:43", genero = "Progressive Rock", albumId = album.id),
                            Cancion(nombre = "Time", artista = "Pink Floyd", duracion = "6:53", genero = "Progressive Rock", albumId = album.id)
                        )
                        "Get Jinxed" -> listOf(
                            Cancion(nombre = "Get Jinxed", artista = "Jinx", duracion = "3:22", genero = "Pop Rock", albumId = album.id),
                            Cancion(nombre = "Legends Never Die", artista = "Against The Current", duracion = "3:55", genero = "Epic Rock", albumId = album.id),
                            Cancion(nombre = "Warriors", artista = "Imagine Dragons", duracion = "2:50", genero = "Alternative Rock", albumId = album.id)
                        )
                        "Bratva" -> listOf(
                            Cancion(nombre = "Brotherhood", artista = "Vladimir", duracion = "3:45", genero = "Dark Trap", albumId = album.id),
                            Cancion(nombre = "Cold Streets", artista = "Vladimir", duracion = "4:10", genero = "Dark Trap", albumId = album.id),
                            Cancion(nombre = "No Mercy", artista = "Vladimir", duracion = "3:58", genero = "Dark Trap", albumId = album.id)
                        )
                        else -> emptyList()
                    }

                    canciones.forEach { cancion ->
                        albumRepository.insertCancion(cancion)
                    }
                }
            }
        }
    }
}
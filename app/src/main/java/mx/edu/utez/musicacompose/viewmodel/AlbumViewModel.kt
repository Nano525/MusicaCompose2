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
import java.io.File

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
                if (albums.isEmpty()) {
                    //nada
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

    // ========== FUNCIONES DE API (READ) ==========

    fun loadAlbumsFromApi() {
        viewModelScope.launch {
            try {
                // Cargar desde la API primero (sin insertar en Room todavía)
                val albumsFromApi = albumRepository.getAlbumsFromApi()
                
                // Solo actualizar si se cargaron datos exitosamente
                if (albumsFromApi.isNotEmpty()) {
                    // Preparar listas de álbumes y canciones para insertar
                    val albumsToInsert = albumsFromApi.map { it.album.copy(id = 0) }
                    val cancionesByAlbumIndex = albumsFromApi.mapIndexed { index, albumConCanciones ->
                        index to albumConCanciones.canciones.map { it.copy(id = 0, albumId = 0) }
                    }.toMap()
                    
                    // Limpiar y reinsertar todo en una transacción atómica
                    albumRepository.replaceAllAlbums(albumsToInsert, cancionesByAlbumIndex)
                }
                // El Flow automáticamente actualizará _Albums cuando se inserten los datos
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ========== FUNCIONES DE API (CREATE) ==========

    fun createAlbumOnApi(
        nombre: String,
        artista: String,
        imagenFile: File? = null
    ) {
        viewModelScope.launch {
            try {
                albumRepository.createAlbumOnApi(
                    nombre = nombre,
                    artista = artista,
                    imagenFile = imagenFile
                )
                // Recargar álbumes desde la API para actualizar la lista
                loadAlbumsFromApi()
            } catch (e: Exception) {
                e.printStackTrace()
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


}
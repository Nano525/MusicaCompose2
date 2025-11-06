package mx.edu.utez.musicacompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mx.edu.utez.musicacompose.data.model.Cancion
import mx.edu.utez.musicacompose.ui.cards.CancionCard
import mx.edu.utez.musicacompose.ui.components.buttons.PrimaryButton
import mx.edu.utez.musicacompose.ui.components.text.Title
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel

@Composable
fun CancionScreen(viewModel: AlbumViewModel, navController: NavController) {
    val album by viewModel.selectedAlbum.collectAsStateWithLifecycle()
    var showDeleteAlbumDialog by remember { mutableStateOf(false) }
    var showDeleteCancionDialog by remember { mutableStateOf(false) }
    var cancionToDelete by remember { mutableStateOf<Cancion?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(33.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
            }
            Title("Canciones de ${album?.album?.nombre ?: "..."}")
        }

        if (album != null) {
            if (album!!.canciones.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(album!!.canciones) { cancion ->
                        CancionCard(
                            cancion = cancion,
                            onEditClick = {
                                viewModel.selectCancion(cancion)
                                viewModel.editarCancion(navController)
                            },
                            onDeleteClick = {
                                cancionToDelete = cancion
                                showDeleteCancionDialog = true
                            }
                        )
                    }
                }
            } else {
                Text(
                    text = "Este álbum no tiene canciones.",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        } else {
            Text(
                text = "Error: No se seleccionó ningún álbum.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        PrimaryButton("Agregar canción") {
            viewModel.agregarCancion(navController)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PrimaryButton("Editar álbum") {
            viewModel.editar(navController)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PrimaryButton("Eliminar álbum") {
            showDeleteAlbumDialog = true
        }
    }

    // Dialog para eliminar álbum
    if (showDeleteAlbumDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteAlbumDialog = false },
            title = { Text("Eliminar álbum") },
            text = { 
                Text("¿Estás seguro de eliminar este álbum? También se eliminarán todas sus canciones.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        album?.album?.id?.let { albumId ->
                            viewModel.deleteAlbum(albumId)
                            viewModel.eliminar(navController)
                        }
                        showDeleteAlbumDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteAlbumDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Dialog para eliminar canción
    if (showDeleteCancionDialog && cancionToDelete != null) {
        AlertDialog(
            onDismissRequest = { 
                showDeleteCancionDialog = false
                cancionToDelete = null
            },
            title = { Text("Eliminar canción") },
            text = { 
                Text("¿Estás seguro de eliminar la canción \"${cancionToDelete?.nombre}\"?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        cancionToDelete?.id?.let { cancionId ->
                            viewModel.deleteCancion(cancionId)
                        }
                        showDeleteCancionDialog = false
                        cancionToDelete = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { 
                        showDeleteCancionDialog = false
                        cancionToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}


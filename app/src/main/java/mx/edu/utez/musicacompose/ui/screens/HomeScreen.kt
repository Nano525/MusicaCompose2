package mx.edu.utez.musicacompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.musicacompose.ui.components.list.AlbumList
import mx.edu.utez.musicacompose.ui.components.text.Title
import mx.edu.utez.musicacompose.ui.theme.MusicaComposeTheme
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel

@Composable
fun HomeScreen(
    viewModel: AlbumViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título de la pantalla
        Title(
            text = "Mis Álbumes",
            fontSize = 28,
            color = Color.Black,
            textAlign = TextAlign.Start
        )
        
        // Lista de álbumes
        if (viewModel.isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (viewModel.error.value.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = viewModel.error.value,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            AlbumList(
                lista = viewModel.albums.value,
                x = { album ->
                    viewModel.selectAlbum(album)
                    navController.navigate("album_detail/${album.id}")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    MusicaComposeTheme {
        val navController = rememberNavController()
        val viewModel = AlbumViewModel()

        HomeScreen(
            viewModel = viewModel,
            navController = navController
        )
    }
}

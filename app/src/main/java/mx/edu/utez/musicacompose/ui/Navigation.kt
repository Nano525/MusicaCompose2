package mx.edu.utez.musicacompose.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mx.edu.utez.musicacompose.ui.screens.AlbumScreen
import mx.edu.utez.musicacompose.ui.screens.HomeScreen
import mx.edu.utez.musicacompose.ui.screens.LoginScreen
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel
import mx.edu.utez.musicacompose.viewmodel.LoginViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    
    val loginViewModel: LoginViewModel = viewModel()
    val albumViewModel: AlbumViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(loginViewModel, navController)
        }
        
        composable("home") {
            HomeScreen(albumViewModel, navController)
        }
        
        composable(
            "album_detail/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.IntType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: 0
            // Buscar el álbum por ID y seleccionarlo
            val album = albumViewModel.albums.value.find { it.id == albumId }
            if (album != null) {
                albumViewModel.selectAlbum(album)
            }
            AlbumScreen(albumViewModel, navController)
        }
    }
}

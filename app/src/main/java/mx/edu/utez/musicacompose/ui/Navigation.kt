package mx.edu.utez.musicacompose.ui

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.musicacompose.MusicaApplication
import mx.edu.utez.musicacompose.data.AppDatabase
import mx.edu.utez.musicacompose.data.repository.AlbumRepository
import mx.edu.utez.musicacompose.ui.screens.AgregarCancionScreen
import mx.edu.utez.musicacompose.ui.screens.AgregarScreen
import mx.edu.utez.musicacompose.ui.screens.CancionScreen
import mx.edu.utez.musicacompose.ui.screens.EditCancionScreen
import mx.edu.utez.musicacompose.ui.screens.EditScreen
import mx.edu.utez.musicacompose.ui.screens.ForgotPasswordScreen
import mx.edu.utez.musicacompose.ui.screens.HomeScreen
import mx.edu.utez.musicacompose.ui.screens.LoginScreen
import mx.edu.utez.musicacompose.ui.screens.RegisterScreen
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModelFactory
import mx.edu.utez.musicacompose.viewmodel.LoginViewModel
import mx.edu.utez.musicacompose.viewmodel.RegisterViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    val loginViewModel: LoginViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()
    
    // Obtener la instancia de la base de datos y crear el repositorio
    val application = context.applicationContext as Application
    val database = (application as? MusicaApplication)?.database 
        ?: AppDatabase.getInstance(application)
    val repository = AlbumRepository(database.albumDao())
    val albumViewModel: AlbumViewModel = viewModel(
        factory = AlbumViewModelFactory(repository)
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(loginViewModel, navController)
        }
        composable("forgot_password") {
            ForgotPasswordScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(viewModel = registerViewModel, navController = navController)
        }
        composable("home") {
            HomeScreen(viewModel = albumViewModel, navController = navController)
        }
        composable("cancion") {
            CancionScreen(viewModel = albumViewModel, navController = navController)
        }
        composable("agregar") {
            AgregarScreen(viewModel = albumViewModel, navController = navController)
        }
        composable("editar") {
            EditScreen(viewModel = albumViewModel, navController = navController)
        }
        composable("agregar_cancion") {
            AgregarCancionScreen(viewModel = albumViewModel, navController = navController)
        }
        composable("editar_cancion") {
            EditCancionScreen(viewModel = albumViewModel, navController = navController)
        }
    }
}

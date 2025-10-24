package mx.edu.utez.musicacompose.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.musicacompose.ui.screens.CancionScreen
import mx.edu.utez.musicacompose.ui.screens.ForgotPasswordScreen
import mx.edu.utez.musicacompose.ui.screens.HomeScreen
import mx.edu.utez.musicacompose.ui.screens.LoginScreen
import mx.edu.utez.musicacompose.ui.screens.RegisterScreen
import mx.edu.utez.musicacompose.viewmodel.AlbumViewModel
import mx.edu.utez.musicacompose.viewmodel.LoginViewModel
import mx.edu.utez.musicacompose.viewmodel.RegisterViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()
    
    val loginViewModel: LoginViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()
    val albumViewModel: AlbumViewModel = viewModel()

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
            AgregarScreen( navController = navController)
        }
        composable("editar") {
            EditScreen( navController = navController)
        }
    }
}

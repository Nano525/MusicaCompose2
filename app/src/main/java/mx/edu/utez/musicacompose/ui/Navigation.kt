package mx.edu.utez.musicacompose.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.musicacompose.ui.screens.LoginScreen
import mx.edu.utez.musicacompose.viewmodel.LoginViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()
    
    val loginViewModel: LoginViewModel = viewModel()


    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(loginViewModel, navController)
        }
       
    }
}

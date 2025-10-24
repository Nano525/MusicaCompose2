package mx.edu.utez.musicacompose.ui

import androidx.compose.runtime.Composable


@Composable
fun Navigation() {
    val navController = rememberNavController()
    
    val loginViewModel: LoginViewModel = viewModel()


    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(loginViewModel, navController)
        }
        composable("passport") {
            PassportScreen(passportViewModel, navController)
        }
        composable("stamp") {
            StampScreen(passportViewModel, navController)
        }
    }
}

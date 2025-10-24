package mx.edu.utez.musicacompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class LoginViewModel : ViewModel() {
    var password = mutableStateOf("")
    var username = mutableStateOf("")
    var loginError = mutableStateOf("")

    fun login(navController: NavController) {
        if (username.value == "admin" && password.value == "123") {
            loginError.value = ""
            navController.navigate("musica") {
                popUpTo("musica") { inclusive = true }
            }
        } else {
            loginError.value = "Algo esta mal en el inicio de sesión"
        }
    }
}
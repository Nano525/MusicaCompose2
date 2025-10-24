package mx.edu.utez.musicacompose.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class RegisterViewModel : ViewModel() {
    var password = mutableStateOf("")
    var username = mutableStateOf("")
    var registerError = mutableStateOf("")

    fun register(onSuccess: () -> Unit){
        if (username.value.isNotEmpty() && password.value.isNotEmpty()) {
            registerError.value = ""
            onSuccess()
        } else {
            registerError.value = "Por favor completa todos los campos"
        }
    }



}
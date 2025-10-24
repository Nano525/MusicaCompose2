package mx.edu.utez.musicacompose.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var password = mutableStateOf("")
    var email = mutableStateOf("")
    var registerError = mutableStateOf("")

    fun register(onSuccess: () -> Unit){

    }

}
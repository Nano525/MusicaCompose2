package mx.edu.utez.musicacompose.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import mx.edu.utez.musicacompose.ui.components.buttons.PrimaryButton
import mx.edu.utez.musicacompose.ui.components.inputs.NumberInputField
import mx.edu.utez.musicacompose.ui.theme.MusicaComposeTheme

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Recupera tu Contraseña", fontWeight= FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Introduce tu dirección de correo electrónico o")
        Text("nombre de usuario y te enviaremos un enlace")
        Text("para volver a tu cuenta")
        Spacer(modifier = Modifier.height(4.dp))
        NumberInputField(
            viewModel = viewModel,
            label = "Correo electrónico"
        )
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton("Enviar") {
            navController.popBackStack()
        }
    }
}

@Preview(showBackground=true)
@Composable
fun previewForgotPassword(){
    MusicaComposeTheme {
        ForgotPasswordScreen()
    }
}





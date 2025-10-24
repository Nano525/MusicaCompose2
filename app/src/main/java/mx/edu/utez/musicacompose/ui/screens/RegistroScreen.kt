package mx.edu.utez.musicacompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import mx.edu.utez.musicacompose.R
import mx.edu.utez.musicacompose.ui.components.buttons.PrimaryButton
import mx.edu.utez.musicacompose.ui.components.image.CircularImage
import mx.edu.utez.musicacompose.ui.components.inputs.PasswordField
import mx.edu.utez.musicacompose.ui.components.inputs.UserInputField
import mx.edu.utez.musicacompose.ui.components.text.Link
import mx.edu.utez.musicacompose.ui.components.text.Title
import mx.edu.utez.musicacompose.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navController: NavController){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ){
        CircularImage(R.drawable.logoapp)
        Title("Aplicacion\nMovil")

        UserInputField(
            value = viewModel.username,
            label = "Correo electronico"
        )

        PasswordField(
            value = viewModel.password,
            label = "Contraseña"
        )

        if(viewModel.registerError.value.isNotEmpty()){
            Text(
                text = viewModel.registerError.value,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Link("¿Has olvidado la contraseña?") {
            navController.navigate("forgot_password")
        }
        PrimaryButton("Registrarse") {
            viewModel.register {
                navController.navigate("menu") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}
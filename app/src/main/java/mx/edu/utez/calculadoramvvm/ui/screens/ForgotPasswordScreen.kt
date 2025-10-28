package mx.edu.utez.calculadoramvvm.ui.screens

import PrimaryButton
import Title
import UserInputField
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.calculadoramvvm.R
import mx.edu.utez.calculadoramvvm.ui.components.images.CircularImage
import mx.edu.utez.calculadoramvvm.ui.theme.CalculadoraMVVMTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.gato.viewmodel.LoginViewModel

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        CircularImage(imageRes = R.drawable.contra, size = 170)

        Title("Recuperar contraseña")

        Text("Ingresa tu correo:")

        UserInputField(
            viewModel = viewModel(),
            label = "Correo"
        )

        PrimaryButton("Recuperar contraseña") {

            navController.navigate("login") {
                popUpTo("forgot_password") { inclusive = true }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PreviewForgotPasswordScreen() {
    CalculadoraMVVMTheme {
        val navController = rememberNavController()
        ForgotPasswordScreen(navController)
    }
}

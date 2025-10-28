package mx.edu.utez.calculadoramvvm.ui.screens

import PrimaryButton
import Title
import UserInputField
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.calculadoramvvm.R
import mx.edu.utez.calculadoramvvm.ui.components.images.CircularImage
import mx.edu.utez.calculadoramvvm.ui.components.inputs.PasswordField
import mx.edu.utez.calculadoramvvm.ui.theme.CalculadoraMVVMTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.gato.viewmodel.LoginViewModel

@Composable
fun RegisterScreen(viewModel: LoginViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        CircularImage(imageRes = R.drawable.administrador_de_tareas_gratis_header, size = 150)

        Title("Bienvenido al registro del gestor de tareas")

        UserInputField(
            viewModel = viewModel,
            label = "Correo"
        )

        PasswordField(
            viewModel = viewModel,
            label = "Contraseña"
        )

        PrimaryButton("Registrarse") {

            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }

        if (viewModel.loginError.value.isNotEmpty()) {
            Text(
                text = viewModel.loginError.value,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    CalculadoraMVVMTheme {
        val navController = rememberNavController()
        val viewModel = LoginViewModel()

        RegisterScreen(viewModel = viewModel, navController = navController)
    }
}

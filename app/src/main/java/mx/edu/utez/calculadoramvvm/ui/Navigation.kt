package mx.edu.utez.calculadoramvvm.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.calculadoramvvm.ui.screens.AddTaskScreen
import mx.edu.utez.calculadoramvvm.ui.screens.ForgotPasswordScreen
import mx.edu.utez.calculadoramvvm.ui.screens.HomeScreen
import mx.edu.utez.calculadoramvvm.ui.screens.LoginScreen
import mx.edu.utez.calculadoramvvm.ui.screens.MenuScreen
import mx.edu.utez.calculadoramvvm.ui.screens.RegisterScreen

import mx.edu.utez.calculadoramvvm.viewmodel.MenuViewModel
import mx.edu.utez.calculadoramvvm.viewmodel.TaskViewModel
import mx.edu.utez.gato.viewmodel.LoginViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val viewModel: LoginViewModel = viewModel() // instancia del ViewModel
            LoginScreen(viewModel = viewModel, navController = navController)
        }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable(route = "register") {
            val viewModel : LoginViewModel=viewModel()
            RegisterScreen(viewModel = viewModel, navController = navController)
        }
        composable("home") { HomeScreen(navController) }



        composable("menu") {
            val viewModel: MenuViewModel = viewModel() // instancia del ViewModel
            MenuScreen(viewModel = viewModel, navController = navController)
        }

        composable("add_task") {
            val taskViewModel: TaskViewModel = viewModel()
            AddTaskScreen(navController, taskViewModel)
        }




    }
}
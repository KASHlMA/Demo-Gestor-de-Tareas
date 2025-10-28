package mx.edu.utez.calculadoramvvm.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController



    @Composable
    fun AddTaskScreen(navController: NavController, viewModel: ViewModel){
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
    }

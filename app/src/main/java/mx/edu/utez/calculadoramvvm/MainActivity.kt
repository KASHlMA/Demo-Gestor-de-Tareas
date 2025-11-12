package mx.edu.utez.calculadoramvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.edu.utez.calculadoramvvm.data.model.AppDatabase
import mx.edu.utez.calculadoramvvm.data.model.TaskRepository
import mx.edu.utez.calculadoramvvm.ui.Navigation   // 👈 IMPORTANTE
import mx.edu.utez.calculadoramvvm.ui.theme.CalculadoraMVVMTheme
import mx.edu.utez.calculadoramvvm.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraMVVMTheme {
                //  Inicializamos la base de datos
                val database = AppDatabase.getDatabase(this)

                // Creamos el repositorio a partir del DAO
                val taskRepository = TaskRepository(database.taskDao())

                //  Creamos el ViewModel usando un Factory
                val taskViewModel = viewModel<TaskViewModel>(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return TaskViewModel(taskRepository) as T
                        }
                    }
                )

                //  Llamamos a Navigation sin pasar parámetros (ya lo crea internamente)
                Navigation()
            }
        }
    }
}

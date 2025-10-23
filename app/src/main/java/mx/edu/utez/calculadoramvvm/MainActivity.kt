package mx.edu.utez.calculadoramvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mx.edu.utez.calculadoramvvm.ui.Navigation
import mx.edu.utez.calculadoramvvm.ui.theme.CalculadoraMVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraMVVMTheme {
                Navigation()
            }
        }
    }
}
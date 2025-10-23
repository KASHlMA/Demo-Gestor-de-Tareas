package mx.edu.utez.calculadoramvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class MenuViewModel: ViewModel(){

    fun goToCalculator(navController: NavController){
        navController.navigate("calculator")
    }


}
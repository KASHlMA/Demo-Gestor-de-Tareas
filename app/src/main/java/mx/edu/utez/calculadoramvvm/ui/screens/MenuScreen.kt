package mx.edu.utez.calculadoramvvm.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.calculadoramvvm.ui.components.InfoCard
import mx.edu.utez.calculadoramvvm.viewmodel.MenuViewModel

@Composable
fun MenuScreen(viewModel: MenuViewModel, navController: NavController){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(30.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()){
            InfoCard(
                "Ejemplo",
                "Continuar aqui",
                Modifier.weight(1f)
                    .clickable{viewModel.goToCalculator(navController)},
                viewModel
            )

        }



    }
}
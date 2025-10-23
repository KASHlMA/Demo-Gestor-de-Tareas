package mx.edu.utez.calculadoramvvm.data.model

import androidx.annotation.DrawableRes
import mx.edu.utez.calculadoramvvm.R

data class User(
    val id: Int,
    val nombre: String,
    val correo: String,
    val telefono: String,
    val sexo: Int,
    @DrawableRes val imagen: Int = R.drawable.femaleprofile
)


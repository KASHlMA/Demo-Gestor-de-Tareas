package mx.edu.utez.calculadoramvvm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.edu.utez.calculadoramvvm.R
import mx.edu.utez.calculadoramvvm.ui.components.images.CircularImage
import mx.edu.utez.calculadoramvvm.viewmodel.MenuViewModel
import mx.edu.utez.calculadoramvvm.data.model.Task
@Composable
fun MenuScreen(viewModel: MenuViewModel, navController: NavController) {
    val tasks by viewModel.tasks.collectAsState(initial = emptyList<Task>())

    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var currentTask by remember { mutableStateOf<Task?>(null) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // ----------- Diálogo de agregar tarea ------------
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Agregar tarea") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título") }
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descripción") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (title.isNotBlank() && description.isNotBlank()) {
                        viewModel.addTask(title, description)
                    }
                    title = ""
                    description = ""
                    showAddDialog = false
                }) {
                    Text("Agregar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // ----------- Diálogo de editar tarea ------------
    if (showEditDialog && currentTask != null) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar tarea") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título") }
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descripción") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    currentTask?.let {
                        viewModel.updateTask(it.copy(title = title, description = description))
                    }
                    showEditDialog = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // ----------- Diálogo de eliminar tarea ------------
    if (showDeleteDialog && currentTask != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar tarea") },
            text = { Text("¿Seguro que deseas eliminar esta tarea?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteTask(currentTask!!)
                    showDeleteDialog = false
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.Black)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularImage(R.drawable.gatocar)

            Spacer(modifier = Modifier.height(10.dp))

            // Botón de cerrar sesión
            IconButton(onClick = {
                navController.navigate("login") {
                    popUpTo("menu") { inclusive = true }
                }
            }) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión", tint = Color.White)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ----------- Lista de tareas dinámicas ------------
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tasks) { task ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Row {
                                    IconButton(onClick = {
                                        currentTask = task
                                        title = task.title
                                        description = task.description
                                        showEditDialog = true
                                    }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Black)
                                    }
                                    IconButton(onClick = {
                                        currentTask = task
                                        showDeleteDialog = true
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Black)
                                    }
                                }
                            }

                            Text(
                                text = task.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

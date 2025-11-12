package mx.edu.utez.calculadoramvvm.data.model

import kotlinx.coroutines.flow.Flow
import mx.edu.utez.calculadoramvvm.data.network.ApiClient
import mx.edu.utez.calculadoramvvm.data.network.TaskApiService
import retrofit2.HttpException
import java.io.IOException

class TaskRepository(private val taskDao: TaskDao) {

    private val api = ApiClient.retrofit.create(TaskApiService::class.java)

    // Devuelve un flujo que observa los cambios en la base de datos local
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    // Sincroniza la base de datos local con el servidor Flask
    private suspend fun syncWithServer() {
        try {
            val response = api.getTasks()
            if (response.isSuccessful && response.body() != null) {
                val remoteList = response.body()!!
                // Limpia la base local y guarda las tareas del servidor
                taskDao.clearAll()
                remoteList.forEach { taskDao.insertTask(it) }
            }
        } catch (e: IOException) {
            e.printStackTrace() // Error de red
        } catch (e: HttpException) {
            e.printStackTrace() // Error HTTP
        } catch (e: Exception) {
            e.printStackTrace() // Otro tipo de error
        }
    }

    // Inserta una tarea nueva y sincroniza con el servidor
    suspend fun insertTask(task: Task) {
        try {
            val response = api.createTask(task)
            if (response.isSuccessful && response.body() != null) {
                taskDao.insertTask(response.body()!!)
            } else {
                // Guarda localmente si falla el servidor
                taskDao.insertTask(task)
            }
            // Sincroniza datos después de guardar
            syncWithServer()
        } catch (e: Exception) {
            e.printStackTrace()
            // Si no hay conexión, guarda localmente
            taskDao.insertTask(task)
        }
    }

    // Actualiza una tarea existente
    suspend fun updateTask(task: Task) {
        try {
            val response = api.updateTask(task.id, task)
            if (response.isSuccessful && response.body() != null) {
                taskDao.insertTask(response.body()!!)
            } else {
                taskDao.insertTask(task)
            }
            syncWithServer()
        } catch (e: Exception) {
            e.printStackTrace()
            taskDao.insertTask(task)
        }
    }

    // Elimina una tarea
    suspend fun deleteTask(task: Task) {
        try {
            val response = api.deleteTask(task.id)
            if (response.isSuccessful) {
                taskDao.deleteTask(task)
            } else {
                taskDao.deleteTask(task)
            }
            syncWithServer()
        } catch (e: Exception) {
            e.printStackTrace()
            taskDao.deleteTask(task)
        }
    }
}

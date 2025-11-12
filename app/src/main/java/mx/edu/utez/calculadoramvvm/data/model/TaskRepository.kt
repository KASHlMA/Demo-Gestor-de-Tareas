package mx.edu.utez.calculadoramvvm.data.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mx.edu.utez.calculadoramvvm.data.network.ApiClient
import mx.edu.utez.calculadoramvvm.data.network.TaskApiService
import retrofit2.HttpException
import java.io.IOException

class TaskRepository(private val taskDao: TaskDao) {

    private val api = ApiClient.retrofit.create(TaskApiService::class.java)

    // Se obtienen las tareas desde el servidor
    fun getAllTasks(): Flow<List<Task>> = flow {
        try {
            // Intentar obtener los datos del servidor remoto (Flask)
            val response = api.getTasks()
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                emit(response.body()!!)
            } else {
                // Aqui se emite lo que hay en la base de datos local
                taskDao.getAllTasks().collect { localList ->
                    emit(localList)
                }
            }
        } catch (e: IOException) {
            // Error de conexión o API apagada
            e.printStackTrace()
            // Se emite la lista local en caso de fallo de red
            taskDao.getAllTasks().collect { localList ->
                emit(localList)
            }
        } catch (e: HttpException) {
            // Error HTTP del servidor (500, 404.)
            e.printStackTrace()
            taskDao.getAllTasks().collect { localList ->
                emit(localList)
            }
        } catch (e: Exception) {
            //Cualquier otro error inesperado
            e.printStackTrace()
            taskDao.getAllTasks().collect { localList ->
                emit(localList)
            }
        }
    }

    suspend fun insertTask(task: Task) {
        try {
            val response = api.createTask(task)
            if (response.isSuccessful && response.body() != null) {
                taskDao.insertTask(response.body()!!)
            } else {
                //Si falla la API, guardar localmente de todas formas
                taskDao.insertTask(task)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            taskDao.insertTask(task)
        }
    }

    suspend fun updateTask(task: Task) {
        try {
            val response = api.updateTask(task.id, task)
            if (response.isSuccessful && response.body() != null) {
                taskDao.insertTask(response.body()!!)
            } else {
                taskDao.insertTask(task)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            taskDao.insertTask(task)
        }
    }

    suspend fun deleteTask(task: Task) {
        try {
            val response = api.deleteTask(task.id)
            if (response.isSuccessful) {
                taskDao.deleteTask(task)
            } else {
                // Si no responde el servidor, borrar localmente de todos modos
                taskDao.deleteTask(task)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            taskDao.deleteTask(task)
        }
    }
}

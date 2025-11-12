package mx.edu.utez.calculadoramvvm.data.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mx.edu.utez.calculadoramvvm.data.network.ApiClient
import mx.edu.utez.calculadoramvvm.data.network.TaskApiService

class TaskRepository(private val taskDao: TaskDao) {

    private val api = ApiClient.retrofit.create(TaskApiService::class.java)

    // Se obtienen las tareas desde el servidor
    fun getAllTasks(): Flow<List<Task>> = flow {
        val response = api.getTasks()
        if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            emit(response.body()!!)
        } else {
            // Aqui se emite lo que hay en la base de datos local
            taskDao.getAllTasks().collect { localList ->
                emit(localList)
            }
        }
    }



    suspend fun insertTask(task: Task) {
        val response = api.createTask(task)
        if (response.isSuccessful) {
            taskDao.insertTask(response.body()!!)
        }
    }

    suspend fun updateTask(task: Task) {
        val response = api.updateTask(task.id, task)
        if (response.isSuccessful) {
            taskDao.insertTask(response.body()!!)
        }
    }

    suspend fun deleteTask(task: Task) {
        val response = api.deleteTask(task.id)
        if (response.isSuccessful) {
            taskDao.deleteTask(task)
        }
    }
}

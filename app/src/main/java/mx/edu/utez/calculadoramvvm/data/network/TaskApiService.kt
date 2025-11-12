package mx.edu.utez.calculadoramvvm.data.network

import mx.edu.utez.calculadoramvvm.data.model.Task
import retrofit2.Response
import retrofit2.http.*

interface TaskApiService {

    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>

    @POST("tasks")
    suspend fun createTask(@Body task: Task): Response<Task>

    @PUT("tasks/{id}")
    suspend fun updateTask(@Path("id") id: Int, @Body task: Task): Response<Task>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Map<String, String>>
}

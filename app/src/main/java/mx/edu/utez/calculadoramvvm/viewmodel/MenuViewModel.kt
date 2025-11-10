package mx.edu.utez.calculadoramvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.calculadoramvvm.data.model.Task
import mx.edu.utez.calculadoramvvm.data.model.AppDatabase
import mx.edu.utez.calculadoramvvm.data.model.TaskRepository

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        val dao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(dao)
        getAllTasks()
    }

    private fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            repository.insertTask(Task(title = title, description = description))
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}

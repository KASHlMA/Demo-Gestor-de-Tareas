package mx.edu.utez.calculadoramvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mx.edu.utez.calculadoramvvm.data.model.Task
import mx.edu.utez.calculadoramvvm.data.model.TaskRepository

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // Estado observable con Compose
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { taskList ->
                _tasks.value = taskList
            }
        }
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            if (title.isNotBlank() && description.isNotBlank()) {
                repository.insertTask(Task(title = title, description = description))
            }
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

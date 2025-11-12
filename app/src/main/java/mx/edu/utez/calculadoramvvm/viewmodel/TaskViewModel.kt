package mx.edu.utez.calculadoramvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import mx.edu.utez.calculadoramvvm.data.model.Task
import mx.edu.utez.calculadoramvvm.data.model.TaskRepository

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect { list ->
                _tasks.value = list
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


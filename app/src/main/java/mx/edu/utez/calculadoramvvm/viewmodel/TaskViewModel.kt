package mx.edu.utez.calculadoramvvm.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import mx.edu.utez.calculadoramvvm.data.model.Task

class TaskViewModel : ViewModel() {
    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> = _tasks

    fun addTask(title: String, description: String) {
        if (title.isNotBlank() && description.isNotBlank()) {
            _tasks.add(Task(id = _tasks.size + 1, title = title, description = description))
        }
    }
}
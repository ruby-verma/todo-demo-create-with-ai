package com.ruby.tododemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ruby.tododemo.data.model.Category
import com.ruby.tododemo.data.model.Task
import com.ruby.tododemo.data.model.TaskWithCategory
import com.ruby.tododemo.data.repository.TodoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class TodoUiState(
    val tasks: List<TaskWithCategory> = emptyList(),
    val categories: List<Category> = emptyList(),
    val pendingCount: Int = 0,
    val totalCount: Int = 0
)

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    val uiState: StateFlow<TodoUiState> = combine(
        repository.tasksWithCategory,
        repository.allCategories,
        repository.pendingTasksCount,
        repository.totalTasksCount
    ) { tasks, categories, pending, total ->
        TodoUiState(tasks, categories, pending, total)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TodoUiState()
    )

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
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

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            repository.insertCategory(category)
        }
    }

    suspend fun getTaskById(id: Long): Task? = repository.getTaskById(id)

    class Factory(private val repository: TodoRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
                return TodoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

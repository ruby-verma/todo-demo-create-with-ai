package com.ruby.tododemo.data.repository

import com.ruby.tododemo.data.dao.CategoryDao
import com.ruby.tododemo.data.dao.TaskDao
import com.ruby.tododemo.data.model.Category
import com.ruby.tododemo.data.model.Task
import com.ruby.tododemo.data.model.TaskWithCategory
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val categoryDao: CategoryDao,
    private val taskDao: TaskDao
) {
    // Categories
    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()

    suspend fun insertCategory(category: Category) = categoryDao.insertCategory(category)
    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)
    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)
    suspend fun getCategoryById(id: Long) = categoryDao.getCategoryById(id)

    // Tasks
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val tasksWithCategory: Flow<List<TaskWithCategory>> = taskDao.getTasksWithCategory()
    val pendingTasksCount: Flow<Int> = taskDao.getPendingTasksCount()
    val totalTasksCount: Flow<Int> = taskDao.getTotalTasksCount()

    fun getTasksByCategory(categoryId: Long): Flow<List<Task>> = taskDao.getTasksByCategory(categoryId)
    suspend fun getTaskById(id: Long) = taskDao.getTaskById(id)
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}

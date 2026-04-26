package com.ruby.tododemo

import android.app.Application
import com.ruby.tododemo.data.database.TodoDatabase
import com.ruby.tododemo.data.repository.TodoRepository

class TodoApplication : Application() {
    val database by lazy { TodoDatabase.getDatabase(this) }
    val repository by lazy { TodoRepository(database.categoryDao(), database.taskDao()) }
}

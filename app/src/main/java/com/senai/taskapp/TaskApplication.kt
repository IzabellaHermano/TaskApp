package com.senai.taskapp

import android.app.Application
import com.senai.taskapp.data.TaskDatabase
import com.senai.taskapp.repository.TaskRepository

class TaskApplication : Application() {
    // Inicialização preguiçosa do Database (Singleton)
    val database by lazy { TaskDatabase.getDatabase(this) }

    // Inicialização preguiçosa do Repository
    val repository by lazy { TaskRepository(database.taskDao()) }
}
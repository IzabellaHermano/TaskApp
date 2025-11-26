package com.senai.taskapp.repository

import com.senai.taskapp.data.TaskDao
import com.senai.taskapp.data.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {

    // A leitura é feita via Flow diretamente do DAO [cite: 1666, 2479]
    val allTasks: Flow<List<TaskEntity>> = dao.getAllTasks()

    // Operações de escrita que usam funções suspend do DAO [cite: 1666, 2481, 2484, 2487]
    suspend fun insert(task: TaskEntity) {
        dao.insert(task)
    }

    suspend fun update(task: TaskEntity) {
        dao.update(task)
    }

    suspend fun delete(task: TaskEntity) {
        dao.delete(task)
    }
}
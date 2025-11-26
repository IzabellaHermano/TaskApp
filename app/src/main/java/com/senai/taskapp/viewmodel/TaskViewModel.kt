package com.senai.taskapp.viewmodel

import androidx.lifecycle.*
import com.senai.taskapp.data.TaskEntity
import com.senai.taskapp.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // Converte o Flow do Repository em LiveData para ser observado pela Activity [cite: 1667, 2515]
    val allTasks = repository.allTasks.asLiveData()

    // Operações CRUD lançadas dentro do viewModelScope (seguro para a Coroutine) [cite: 2549]
    fun insert(title: String) = viewModelScope.launch {
        repository.insert(TaskEntity(title = title))
    }

    fun update(task: TaskEntity) = viewModelScope.launch {
        repository.update(task)
    }

    fun delete(task: TaskEntity) = viewModelScope.launch {
        repository.delete(task)
    }
}


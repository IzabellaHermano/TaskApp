package com.senai.taskapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    // C - Create (Insert)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity)

    // R - Read (Select All)
    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getAllTasks(): Flow<List<TaskEntity>> // Retorna um Flow reativo [cite: 1770, 2408]

    // U - Update
    @Update
    suspend fun update(task: TaskEntity)

    // D - Delete
    @Delete
    suspend fun delete(task: TaskEntity)

    // D - Delete All (Opcional)
    @Query("DELETE FROM task_table")
    suspend fun deleteAll()
}
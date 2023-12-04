package com.example.tasksapplication.data.datasource.task.local.RoomDb

import android.content.ContentValues.TAG
import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: TaskDbo): Long

    @Query("SELECT * FROM tasks")
    fun getTasks(): List<TaskDbo>

    @Update
    fun updateTask(task: TaskDbo)


    @Delete
    fun deleteTask(task: TaskDbo)

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskById(taskId: Long): TaskDbo
}
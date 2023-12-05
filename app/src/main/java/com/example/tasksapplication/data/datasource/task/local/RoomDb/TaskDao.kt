package com.example.tasksapplication.data.datasource.task.local.RoomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTaskAndGetId(task: TaskDbo): Long

    @Query("SELECT * FROM tasks")
    fun getTasks(): List<TaskDbo>

    @Update
    fun updateTask(task: TaskDbo)

    @Delete
    fun deleteTask(task: TaskDbo)

    @Query("DELETE FROM tasks WHERE body = ''")
    fun removeTasksWithNoText()

}
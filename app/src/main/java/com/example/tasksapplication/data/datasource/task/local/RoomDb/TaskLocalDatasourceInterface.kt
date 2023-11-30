package com.example.tasksapplication.data.datasource.task.local.RoomDb

interface TaskLocalDatasourceInterface {

    suspend fun getTasks(): List<TaskDbo>

    suspend fun deleteTask(task: TaskDbo)
    suspend fun updateTask(taskDbo: TaskDbo)
    suspend fun insertTask(taskDbo: TaskDbo): Long
    suspend fun getTaskBy(id: Long): TaskDbo
}
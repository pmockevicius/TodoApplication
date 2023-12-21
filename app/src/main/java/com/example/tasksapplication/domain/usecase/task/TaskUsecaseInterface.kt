package com.example.tasksapplication.domain.usecase.task

import com.example.tasksapplication.domain.entity.Task

interface TaskUsecaseInterface {
    suspend fun getTasks(): List<Task>
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(body: String,  task: Task)
    suspend fun createAndGetTask(): Task
    suspend fun removeTasksWithNoText()
}
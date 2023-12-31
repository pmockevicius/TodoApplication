package com.example.tasksapplication.domain.usecase.task

import com.example.tasksapplication.domain.entity.Task

interface TaskUsecaseInterface {
    suspend fun getTasks(): List<Task>
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun insertTask(task: Task): Long
    suspend fun getTaskById(id: Long): Task
}
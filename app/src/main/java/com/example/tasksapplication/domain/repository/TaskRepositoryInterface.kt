package com.example.tasksapplication.domain.repository

import com.example.tasksapplication.domain.entity.Task

interface TaskRepositoryInterface {

    suspend fun getTasks(): List<Task>

    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun createTaskAndGetId(task: Task): Long
    suspend fun removeTasksWithNoText()
}
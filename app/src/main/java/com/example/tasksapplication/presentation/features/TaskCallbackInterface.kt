package com.example.tasksapplication.presentation.features

import com.example.tasksapplication.domain.entity.Task


interface TaskCallbackInterface {

    fun deleteTask(task: Task, position: Int)
    fun updateTask(title: String, description: String, task: Task)
}
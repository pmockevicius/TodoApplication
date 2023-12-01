package com.example.tasksapplication.presentation.features

import com.example.tasksapplication.domain.entity.Task


interface TaskCallbackInterface {

    fun deleteTask(task: Task)
    fun updateTask(title: String, task: Task)
    fun toogleCompletedStatus(isChecked: Boolean, task: Task)
}
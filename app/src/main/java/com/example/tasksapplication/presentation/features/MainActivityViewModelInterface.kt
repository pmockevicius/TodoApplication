package com.example.tasksapplication.presentation.features

import androidx.lifecycle.LiveData
import com.example.tasksapplication.domain.entity.Task

interface MainActivityViewModelInterface {

    val task: LiveData<List<Task>>
    fun getTasks()
    fun deleteTask(task: Task)

    fun updateTask(title: String, description: String, task: Task)
    fun insertTask(task: Task)
}
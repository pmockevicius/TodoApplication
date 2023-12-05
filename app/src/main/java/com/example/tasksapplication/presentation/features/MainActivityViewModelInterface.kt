package com.example.tasksapplication.presentation.features

import com.example.tasksapplication.domain.entity.Task
import kotlinx.coroutines.flow.StateFlow

interface MainActivityViewModelInterface {

    val uiState: StateFlow<UIState>
    fun getTasks()
    fun deleteTask(task: Task)

    fun updateTask(taskBody: String, task: Task)
    fun addNewTask()
    fun toggleCompletedTaskVisibility(displayCompleted: Boolean)
    fun toggleTaskCompletedStatus(isChecked: Boolean, task: Task)
    fun removeEmptyTasks()


}
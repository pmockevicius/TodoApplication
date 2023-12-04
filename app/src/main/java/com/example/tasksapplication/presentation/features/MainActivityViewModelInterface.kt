package com.example.tasksapplication.presentation.features

import androidx.lifecycle.LiveData
import com.example.tasksapplication.domain.entity.Task
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.StateFlow

interface MainActivityViewModelInterface {

    val uiState: StateFlow<UIState>
    fun getTasks()
    fun deleteTask(task: Task)

    fun updateTask(title: String, task: Task)
    fun insertTask(task: Task)
    fun toogleCompletedTaskVisibility(displayCompleted: Boolean)
    fun toogleCompletedStatus(isChecked: Boolean, task: Task): Boolean

}
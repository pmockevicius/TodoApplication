package com.example.tasksapplication.presentation.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.domain.usecase.task.TaskUsecaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class UIState(
    val tasks: List<Task> = listOf(),
    val newTaskId: Long = 0,
)


@HiltViewModel
class MainActivityViewModel @Inject constructor(private val usecase: TaskUsecaseInterface) :
    MainActivityViewModelInterface, ViewModel() {

    private var displayCompletedTasks: Boolean = false


    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState())
    override val uiState: StateFlow<UIState> = _uiState.asStateFlow()


    override fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = if (displayCompletedTasks) {
                usecase.getTasks()
            } else {
                usecase.getTasks().filter { task -> !task.isCompleted }
            }
            withContext(Dispatchers.Main) {
                updateUIState(tasks = tasks)
            }
        }
    }

    override fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            usecase.deleteTask(task)
        }
    }

    override fun updateTask(taskBody: String, task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            usecase.updateTask(taskBody, task)
        }
    }


    override fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            val insertedTaskId = usecase.insertTask(task)
            updateUIState(newTaskId = insertedTaskId )
        }
    }


    override fun toogleCompletedTaskVisibility(displayCompleted: Boolean) {
        displayCompletedTasks = displayCompleted
        getTasks()
    }


    override fun toogleCompletedStatus(isChecked: Boolean, task: Task): Boolean {

        when {
            isChecked && !displayCompletedTasks -> {
                task.isCompleted = true
                updateTask(task.body, task)
                return true
            }

            isChecked && displayCompletedTasks -> {
                task.isCompleted = true
                updateTask(task.body, task)
                return false
            }

            else -> {
                task.isCompleted = false
                updateTask(task.body, task)
                return false
            }
        }


    }

    private fun updateUIState(
        tasks: List<Task> = _uiState.value.tasks,
        newTaskId: Long = _uiState.value.newTaskId

    ) {
        _uiState.value = UIState(
            tasks = tasks,
            newTaskId = newTaskId
        )
    }

}
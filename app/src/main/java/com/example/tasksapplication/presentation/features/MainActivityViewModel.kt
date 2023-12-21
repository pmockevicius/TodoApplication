package com.example.tasksapplication.presentation.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.domain.usecase.task.TaskUsecaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class UIState(
    val tasks: List<Task> = listOf(),
    val newTask: Task = Task(),
    val taskToBeRemoved: Task = Task()
)

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val usecase: TaskUsecaseInterface) :
    MainActivityViewModelInterface, ViewModel() {

    private var displayCompletedTasks: Boolean = false

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState())
    override val uiState: StateFlow<UIState> = _uiState

    override fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            removeEmptyTasks()                                         //Where to implement this logic?
            val tasks = if (displayCompletedTasks) {
                usecase.getTasks().sortedBy { task -> task.isCompleted}
            } else {
                usecase.getTasks().filter { task -> !task.isCompleted }
            }
            withContext(Dispatchers.Main) {
//                updateUIState(tasks = tasks)

                _uiState.update { it.copy(tasks = tasks) }
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

    override fun addNewTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val taskToBeAddedToUI = usecase.createAndGetTask()

            _uiState.update { it.copy(newTask = taskToBeAddedToUI) }

        }
    }

    override fun toggleCompletedTaskVisibility(displayCompleted: Boolean) {
        displayCompletedTasks = displayCompleted
        getTasks()
    }

    override fun toggleTaskCompletedStatus(isChecked: Boolean, task: Task) {
        task.isCompleted = isChecked

        viewModelScope.launch(Dispatchers.IO) {
            updateTask(task.body, task)

            withContext(Dispatchers.Main) {
                if (isChecked && !displayCompletedTasks) {
                    _uiState.update { it.copy(taskToBeRemoved = task) }

                }
            }
        }
    }

    override fun removeEmptyTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            usecase.removeTasksWithNoText()
        }
    }



}
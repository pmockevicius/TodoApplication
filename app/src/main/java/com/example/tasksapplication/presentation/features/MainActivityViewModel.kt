package com.example.tasksapplication.presentation.features

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.domain.usecase.task.TaskUsecaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val usecase: TaskUsecaseInterface) :
    MainActivityViewModelInterface, ViewModel() {

    private var _task = MutableLiveData<List<Task>>()
    override val task: LiveData<List<Task>> = _task
    override fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = usecase.getTasks().filter { task -> !task.isCompleted }
            _task.postValue(tasks)
        }
    }

    override fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            usecase.deleteTask(task)
        }
    }

    override fun updateTask(title: String, task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            usecase.updateTask(title, task)
        }
    }


    override fun insertTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            usecase.insertTask(task)
            val updatedTasks = usecase.getTasks()
            _task.postValue(updatedTasks)
        }
    }

    override fun toogleCompletedTaskVisibility(displayCompletedTasks: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = usecase.getTasks()
            val filteredTasks = if (displayCompletedTasks) {
                tasks
            } else {
                tasks.filter { !it.isCompleted }
            }
            _task.postValue(filteredTasks)
        }
    }

}
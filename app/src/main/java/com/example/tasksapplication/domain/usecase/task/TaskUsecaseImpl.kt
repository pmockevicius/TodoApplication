package com.example.tasksapplication.domain.usecase.task

import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskDbo
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.domain.repository.TaskRepositoryInterface
import javax.inject.Inject

class TaskUsecaseImpl @Inject constructor (private val repository: TaskRepositoryInterface) : TaskUsecaseInterface {

    override suspend fun insertTask(task: Task): Long{
       return repository.insertTask(task)
    }
    override suspend fun getTasks(): List<Task> {
        return repository.getTasks()
    }

    override suspend fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    override suspend fun updateTask(title: String, description: String, task: Task) {
        task.title = title
        task.description = description
        repository.updateTask(task)
    }


    override suspend fun getTaskById(id: Long): Task{
        return repository.getTaskById(id)
    }
}
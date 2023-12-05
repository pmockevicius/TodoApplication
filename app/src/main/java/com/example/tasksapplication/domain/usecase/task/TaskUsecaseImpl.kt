package com.example.tasksapplication.domain.usecase.task

import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.domain.repository.TaskRepositoryInterface
import javax.inject.Inject

class TaskUsecaseImpl @Inject constructor (private val repository: TaskRepositoryInterface) : TaskUsecaseInterface {

    override suspend fun insertToDbAndGenerateTaskForUI(): Task {
        val newTaskId = repository.insertTaskAndGetId(Task())
        return Task(id = newTaskId, timeAdded = System.currentTimeMillis())
    }


    override suspend fun getTasks(): List<Task> {
        return repository.getTasks()
    }

    override suspend fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    override suspend fun updateTask(body: String,  task: Task) {
        task.apply {
            this.body = body
            repository.updateTask(this)
        }
    }

    override suspend fun removeTasksWithNoText() {
      repository.removeTasksWithNoText()
    }


}
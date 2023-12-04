package com.example.tasksapplication.domain.usecase.task

import android.content.ContentValues.TAG
import android.util.Log
import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskDbo
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.domain.repository.TaskRepositoryInterface
import javax.inject.Inject

class TaskUsecaseImpl @Inject constructor (private val repository: TaskRepositoryInterface) : TaskUsecaseInterface {

    override suspend fun insertTask(task: Task): Long{
        val newId =  repository.insertTask(task)
        Log.d(TAG, "insertTask: $newId")
       return newId
    }
    override suspend fun getTasks(): List<Task> {
        return repository.getTasks()
    }

    override suspend fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    override suspend fun updateTask(body: String,  task: Task) {
//        Log.d(TAG, "usecase before merge: $task ")
        task.body = body
//        Log.d(TAG, "usecase after merge: $task ")
        repository.updateTask(task)
    }



}
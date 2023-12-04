package com.example.tasksapplication.data.datasource.task.local.RoomDb

import android.content.ContentValues
import android.util.Log
import com.sidharth.mosam.data.local.TaskDataBase
import javax.inject.Inject

class TaskLocalDatasourceImpl @Inject constructor(database: TaskDataBase): TaskLocalDatasourceInterface {

    private val taskDao = database.taskDao()

    override suspend fun insertTask(taskDbo: TaskDbo): Long{
        return taskDao.insertTask(taskDbo)
    }
    override suspend fun getTasks() : List<TaskDbo> {
   return taskDao.getTasks()
    }

    override suspend fun deleteTask(task: TaskDbo) {
        taskDao.deleteTask(task)
    }

    override suspend fun updateTask(taskDbo: TaskDbo) {
        Log.d(ContentValues.TAG, "Database: $taskDbo ")
        taskDao.updateTask(taskDbo)
    }

    override suspend fun getTaskBy(id: Long): TaskDbo{
        return taskDao.getTaskById(id)
    }
}
package com.example.tasksapplication.data.datasource.task.local.RoomDb

import com.sidharth.mosam.data.local.TaskDataBase
import javax.inject.Inject

class TaskLocalDatasourceImpl @Inject constructor(
    database: TaskDataBase): TaskLocalDatasourceInterface {

    private val taskDao = database.taskDao()

    override suspend fun insertTaskAndGetId(taskDbo: TaskDbo): Long{
        return taskDao.insertTaskAndGetId(taskDbo)
    }

    override suspend fun removeTasksWithNoText() {
        taskDao.removeTasksWithNoText()
    }

    override suspend fun getTasks() : List<TaskDbo> {
   return taskDao.getTasks()
    }

    override suspend fun deleteTask(task: TaskDbo) {
        taskDao.deleteTask(task)
    }

    override suspend fun updateTask(taskDbo: TaskDbo) {
        taskDao.updateTask(taskDbo)
    }

}
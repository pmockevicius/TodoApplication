package com.example.tasksapplication.data.repository.task

import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskLocalDatasourceInterface
import com.example.tasksapplication.data.repository.mappers.toDbo
import com.example.tasksapplication.data.repository.mappers.toEntity
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.domain.repository.TaskRepositoryInterface
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val localDS: TaskLocalDatasourceInterface) :
    TaskRepositoryInterface {

    override suspend fun insertTask(task: Task):Long{
        return localDS.insertTask(task.toDbo())
    }
    override suspend fun getTasks(): List<Task> {
        val tasksDbos = localDS.getTasks()
        return tasksDbos.map { it.toEntity() }
    }

    override suspend fun deleteTask(task: Task) {
        localDS.deleteTask(task.toDbo())
    }


    override suspend fun updateTask(task: Task) {
        localDS.updateTask(task.toDbo())
    }

    override suspend fun getTaskById(id: Long): Task{
        return localDS.getTaskBy(id).toEntity()
    }
}
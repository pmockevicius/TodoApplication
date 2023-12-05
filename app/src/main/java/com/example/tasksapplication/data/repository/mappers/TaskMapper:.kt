package com.example.tasksapplication.data.repository.mappers

import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskDbo
import com.example.tasksapplication.domain.entity.Task

fun TaskDbo.toEntity(): Task =
    Task(
        id = this.id,
        body = this.body,
        isCompleted = this.isCompleted,
        timeAdded = this.timeAdded
    )

fun Task.toDbo(): TaskDbo =
    TaskDbo(
        id = this.id,
        body = this.body,
        isCompleted = this.isCompleted,
        timeAdded = System.currentTimeMillis()
    )
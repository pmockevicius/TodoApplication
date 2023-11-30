package com.example.tasksapplication.data.repository.mappers

import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskDbo
import com.example.tasksapplication.domain.entity.Task

fun TaskDbo.toEntity(): Task =
    Task(
        id = this.id,
        title = this.title,
        description = this.description,
        isImportant = this.isImportant
    )


fun Task.toDbo(): TaskDbo =
    TaskDbo(
        id = this.id,
        title = this.title,
        description= this.description,
        timeAdded = System.currentTimeMillis(),
        isImportant = this.isImportant
    )
package com.example.tasksapplication.domain.entity

data class Task(
    var id: Long = 0,
    var body: String = "",
    var isCompleted: Boolean = false,
    val timeAdded: Long = 0,
    var isImportant: Boolean = false
)

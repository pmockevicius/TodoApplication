package com.example.tasksapplication.domain.entity

data class Task(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var isImportant: Boolean = false
)

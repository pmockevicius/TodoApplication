package com.example.tasksapplication.data.datasource.task.local.RoomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class TaskDbo(


    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    @ColumnInfo(name = "time_added") var timeAdded: Long,
    @ColumnInfo(name = "is_important") var isImportant: Boolean,
) {
}
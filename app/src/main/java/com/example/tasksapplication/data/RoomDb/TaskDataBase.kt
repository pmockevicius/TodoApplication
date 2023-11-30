package com.sidharth.mosam.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskDao
import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskDbo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Database(entities = [TaskDbo::class], version = 1, exportSchema = false)
abstract class TaskDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDataBase? = null

        fun getDatabase(context: Context): TaskDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDataBase::class.java,
                    "Task"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

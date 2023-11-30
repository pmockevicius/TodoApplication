package com.example.tasksapplication.utils.helpers

import android.app.Application
import com.sidharth.mosam.data.local.TaskDataBase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TaskApplication : Application() {
    val database: TaskDataBase by lazy { TaskDataBase.getDatabase(applicationContext) }

}
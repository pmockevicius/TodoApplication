package com.example.tasksapplication.di

import android.content.Context
import androidx.room.Room
import com.sidharth.mosam.data.local.TaskDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideTaskDataBase(@ApplicationContext context: Context): TaskDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            TaskDataBase::class.java,
            "Task"
        ).build()
    }





}
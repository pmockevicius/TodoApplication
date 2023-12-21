package com.example.tasksapplication.di

import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskLocalDatasourceImpl
import com.example.tasksapplication.data.datasource.task.local.TaskLocalDatasourceInterface
import com.sidharth.mosam.data.local.TaskDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModel {

    @Provides
    fun provideLocalTaskDataSource(dataBase: TaskDataBase): TaskLocalDatasourceInterface =
        TaskLocalDatasourceImpl(
            dataBase
        )
}
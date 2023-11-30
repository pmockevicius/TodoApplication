package com.example.tasksapplication.di

import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskLocalDatasourceInterface
import com.example.tasksapplication.data.repository.task.TaskRepositoryImpl
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.domain.repository.TaskRepositoryInterface
import com.example.tasksapplication.domain.usecase.task.TaskUsecaseImpl
import com.example.tasksapplication.domain.usecase.task.TaskUsecaseInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModel {

    @Provides
    fun provideTaskRepository(localDS: TaskLocalDatasourceInterface): TaskRepositoryInterface =
        TaskRepositoryImpl(
            localDS
        )
}

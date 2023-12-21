package com.example.tasksapplication.di

import com.example.tasksapplication.data.datasource.task.local.TaskLocalDatasourceInterface
import com.example.tasksapplication.data.repository.task.TaskRepositoryImpl
import com.example.tasksapplication.domain.repository.TaskRepositoryInterface
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

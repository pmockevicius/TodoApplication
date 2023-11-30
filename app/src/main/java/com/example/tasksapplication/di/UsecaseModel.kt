package com.example.tasksapplication.di

import com.example.tasksapplication.domain.repository.TaskRepositoryInterface
import com.example.tasksapplication.domain.usecase.task.TaskUsecaseImpl
import com.example.tasksapplication.domain.usecase.task.TaskUsecaseInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UsecaseModel {

    @Provides
    fun provideLocalTaskUsecase(repository: TaskRepositoryInterface): TaskUsecaseInterface =
        TaskUsecaseImpl(repository)
}
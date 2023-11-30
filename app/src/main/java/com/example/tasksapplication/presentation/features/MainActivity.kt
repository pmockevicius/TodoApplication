package com.example.tasksapplication.presentation.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.presentation.features.details.TaskAdapter
import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskLocalDatasourceImpl
import com.example.tasksapplication.data.datasource.task.local.RoomDb.TaskLocalDatasourceInterface
import com.example.tasksapplication.data.repository.task.TaskRepositoryImpl
import com.example.tasksapplication.databinding.ActivityMainBinding
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.domain.repository.TaskRepositoryInterface
import com.example.tasksapplication.domain.usecase.task.TaskUsecaseImpl
import com.example.tasksapplication.domain.usecase.task.TaskUsecaseInterface
import com.example.tasksapplication.utils.helpers.TaskApplication
import com.sidharth.mosam.data.local.TaskDataBase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        setContentView(binding.root)

        initObservers()
        initListeners()
        initView()
    }

    private fun initView() {
        viewModel.getTasks()
        setUpTaskAdapter()
    }


    private fun initObservers() {
        viewModel.task.observe(this) {
            taskAdapter.updateTasks(it.toMutableList())
        }
    }

    private fun setUpTaskAdapter() {
        val recyclerView = binding.tasksRecyclerView
       taskAdapter = TaskAdapter(mutableListOf(), TaskCallbacks())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter
    }

    private inner class TaskCallbacks : TaskCallbackInterface {
        override fun deleteTask(task: Task, position: Int) {
            viewModel.deleteTask(task)
            taskAdapter.removeTask(position)

        }

        override fun updateTask(title: String, description: String, task: Task) {
            viewModel.updateTask(title, description, task)
        }
    }

    private fun initListeners(){
        binding.floatingAdd.setOnClickListener(){
            val defaultTask = Task(title = "Add task title", description = "Add task Description", isImportant = false)
            viewModel.insertTask(defaultTask)
        }
    }


}
package com.example.tasksapplication.presentation.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.presentation.features.details.TaskAdapter
import com.example.tasksapplication.databinding.ActivityMainBinding
import com.example.tasksapplication.domain.entity.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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

        initView()
    }

    private fun initView() {
        viewModel.getTasks()
        initObservers()
        setUpTaskAdapter()
        initListeners()
    }


    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map { it.tasks }
                    .distinctUntilChanged().collect {
                        taskAdapter.updateTasks(it.toMutableList())
                    }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map { it.newTask }
                    .distinctUntilChanged().collect {
                        taskAdapter.insertTask(it)
                    }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.map { it.taskToBeRemoved }
                    .distinctUntilChanged().collect {
                        taskAdapter.removeTask(it)
                    }
            }
        }
    }

    private fun initListeners() {
        binding.floatingAddButton.setOnClickListener() {
            viewModel.addNewTask()
        }

        binding.materialSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.toggleCompletedTaskVisibility(isChecked)
        }
    }

    private fun setUpTaskAdapter() {
        val recyclerView = binding.tasksRecyclerView
        taskAdapter = TaskAdapter(mutableListOf(), TaskCallbacks(), recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter
    }

    private inner class TaskCallbacks : TaskCallbackInterface {
        override fun deleteTask(task: Task) {
            viewModel.deleteTask(task)
            taskAdapter.removeTask(task)
        }

        override fun updateTask(taskBody: String, task: Task) {
            viewModel.updateTask(taskBody, task)
        }

        override fun toggleCompletedStatus(isChecked: Boolean, task: Task) {
            viewModel.toggleTaskCompletedStatus(isChecked, task)
        }
    }
}
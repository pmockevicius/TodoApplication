package com.example.tasksapplication.presentation.features

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmorty.presentation.features.details.TaskAdapter

import com.example.tasksapplication.databinding.ActivityMainBinding
import com.example.tasksapplication.domain.entity.Task
import com.google.android.material.switchmaterial.SwitchMaterial

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var completedTasksSwitch: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        completedTasksSwitch = binding.materialSwitch

        setContentView(binding.root)

        initObservers()
        initListeners()
        initView()
    }

    private fun initView() {


        viewModel.getTasks()
        setUpTaskAdapter()
        initListeners()
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
        override fun deleteTask(task: Task) {
            viewModel.deleteTask(task)
            taskAdapter.removeTask(task)
        }

        override fun updateTask(title: String, task: Task) {
            viewModel.updateTask(title, task)
        }

        val handler = Handler()
        val delayedOperationsMap = mutableMapOf<Task, Runnable>()

        override fun toogleCompletedStatus(isChecked: Boolean, task: Task) {

            // Remove any existing delayed operation for the same task
            val existingRunnable = delayedOperationsMap.remove(task)
            if (existingRunnable != null) {
                handler.removeCallbacks(existingRunnable)
            }

            if (isChecked) {
                // Schedule a new delayed operation to delete the task
                val newRunnable = Runnable {
//                    viewModel.deleteTask(task)
                    taskAdapter.removeTask(task)
                    task.isCompleted = true
                    viewModel.updateTask(task.body, task)
                }

                handler.postDelayed(newRunnable, 5000L)

                // Store the Runnable associated with this task
                delayedOperationsMap[task] = newRunnable
            }
        }


    }

    private fun initListeners() {
        binding.floatingAdd.setOnClickListener() {
            viewModel.insertTask(Task())
        }

      completedTasksSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.toogleCompletedTaskVisibility(isChecked)

        }
    }
}
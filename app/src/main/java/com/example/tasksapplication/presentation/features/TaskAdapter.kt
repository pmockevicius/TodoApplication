package com.example.rickmorty.presentation.features.details


import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapplication.R
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.presentation.features.TaskCallbackInterface
import com.google.android.material.button.MaterialButton

class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val taskCallback: TaskCallbackInterface
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskTitle.setText(task.title)
        holder.taskDescription.setText((task.description))


        holder.taskDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                taskCallback.updateTask(task.title, s.toString(), task)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        holder.taskTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                taskCallback.updateTask(s.toString(), task.description, task)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })



        holder.deleteButton.setOnClickListener {
            taskCallback.deleteTask(task, position)
        }
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle: EditText = view.findViewById(R.id.taskTitle)
        val taskDescription: EditText = view.findViewById(R.id.taskDescription)
        val deleteButton: MaterialButton = view.findViewById(R.id.deleteButton)
    }

    fun removeTask(position: Int ) {
        tasks.removeAt(position)
//        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun addTask(task: Task){
        tasks.add( 0, task)
       notifyItemInserted(0)
    }

    fun updateTasks(taskList: MutableList<Task>){
        tasks = taskList
        notifyDataSetChanged()
    }
}
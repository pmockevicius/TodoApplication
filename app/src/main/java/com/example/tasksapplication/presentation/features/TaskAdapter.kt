package com.example.rickmorty.presentation.features.details


import android.content.ContentValues.TAG
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapplication.R
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.presentation.features.TaskCallbackInterface
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.log

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
        holder.taskTitle.setText(task.body)
        holder.taskTimeAdded.setText(convertMillisToFormattedDateTime(task.timeAdded))
        holder.completedCheckBox.isChecked = task.isCompleted

        holder.bind(task)

        holder.taskTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                taskCallback.updateTask(s.toString(), task)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })




    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle: EditText = view.findViewById(R.id.taskTitle)
        val taskTimeAdded: TextView = view.findViewById(R.id.taskTimeAdded)
        val completedCheckBox: MaterialCheckBox = view.findViewById(R.id.completedCheckbox)


        fun bind(task: Task) {
            completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
                // Update the completed status of the task when the checkbox changes
                taskCallback.toogleCompletedStatus(isChecked, task)
            }
        }


    }

    fun removeTask(task: Task ) {
        val taskPosition = tasks.indexOf(task)
        tasks.removeAt(taskPosition)
        notifyItemRemoved(taskPosition)
    }

    fun updateTasks(taskList: MutableList<Task>){
        tasks = taskList
        notifyDataSetChanged()
    }


    private fun convertMillisToFormattedDateTime(millis: Long): String {
        val dateFormat = SimpleDateFormat("M/d/yy, h:mm a", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return dateFormat.format(calendar.time)
    }
}
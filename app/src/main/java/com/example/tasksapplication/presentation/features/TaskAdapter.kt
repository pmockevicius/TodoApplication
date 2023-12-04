package com.example.rickmorty.presentation.features.details

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapplication.R
import com.example.tasksapplication.domain.entity.Task
import com.example.tasksapplication.presentation.features.TaskCallbackInterface
import com.google.android.material.checkbox.MaterialCheckBox
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val taskCallback: TaskCallbackInterface,
    private val recyclerView: RecyclerView?
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var areListenersActive = true

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
        areListenersActive = false
        holder.taskTitle.setText(task.body)
        holder.taskTimeAdded.setText(convertMillisToFormattedDateTime(task.timeAdded))
        holder.completedCheckBox.isChecked = task.isCompleted
        areListenersActive = true

        initListeners(holder, task)
    }

    private fun initListeners(holder: TaskViewHolder, task: Task) {
        holder.taskTitle.removeTextChangedListener(holder.textWatcher) // Remove the previous listener if any

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("1", "onTextChanged: Called for ${task.id}")
                if (areListenersActive) {
                    taskCallback.updateTask(s.toString(), task)
                }
            }
        }

        holder.textWatcher = textWatcher // Save the TextWatcher instance in the ViewHolder
        holder.taskTitle.addTextChangedListener(textWatcher)

        holder.deleteButton.setOnClickListener {
            taskCallback.deleteTask(task)
        }

        holder.completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (areListenersActive) {
                taskCallback.toggleCompletedStatus(isChecked, task)
            }
        }
    }

    fun removeTask(task: Task) {
        val taskPosition = tasks.indexOf(task)
        tasks.removeAt(taskPosition)
        notifyItemRemoved(taskPosition)
    }

    fun updateTasks(taskList: MutableList<Task>) {
        tasks = taskList
        notifyDataSetChanged()
    }

    fun insertTask(task: Task) {
        tasks.add(tasks.size, task)
        notifyItemInserted(tasks.size)
        moveCursorToNewTask()
    }

    private fun moveCursorToNewTask() {
        recyclerView?.smoothScrollToPosition(tasks.size - 1)

        recyclerView?.post {
            val lastViewHolder =
                recyclerView.findViewHolderForAdapterPosition(tasks.size - 1) as TaskViewHolder?
            lastViewHolder?.taskTitle?.requestFocus()
        }
    }

    private fun convertMillisToFormattedDateTime(millis: Long): String {
        val dateFormat = SimpleDateFormat("M/d/yy, h:mm a", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return dateFormat.format(calendar.time)
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle: EditText = view.findViewById(R.id.taskTitle)
        val taskTimeAdded: TextView = view.findViewById(R.id.taskTimeAdded)
        val completedCheckBox: MaterialCheckBox = view.findViewById(R.id.completedCheckbox)
        val deleteButton: ImageView = view.findViewById(R.id.deleteBtn)
        var textWatcher: TextWatcher? = null
    }
}

package com.senai.taskapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.senai.taskapp.data.TaskEntity

// Recebe funções lambda para comunicar os eventos de clique para a Activity/ViewModel [cite: 1675, 2169]
class TaskAdapter(
    private val onChecked: (TaskEntity) -> Unit,
    private val onDelete: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTaskTitle)
        private val check: CheckBox = itemView.findViewById(R.id.cbTaskCompleted)
        private val delete: ImageButton = itemView.findViewById(R.id.btnDeleteTask)

        fun bind(task: TaskEntity) {
            // Configura o título e o status
            title.text = task.title
            // Remove o listener temporariamente para evitar loops ao reconfigurar
            check.setOnCheckedChangeListener(null)
            check.isChecked = task.isCompleted

            // Aplica ou remove o risco no texto (strikethrough) [cite: 2167]
            if (task.isCompleted) {
                title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            // Listener para o CheckBox (operação de Update)
            check.setOnCheckedChangeListener { _, isChecked ->
                // Cria uma nova TaskEntity com o status atualizado [cite: 2167]
                val updatedTask = task.copy(isCompleted = isChecked)
                onChecked(updatedTask)
            }

            // Listener para o botão de Delete (operação de Delete)
            delete.setOnClickListener {
                onDelete(task)
            }
        }
    }
}

// Classe para otimizar a atualização do RecyclerView [cite: 2168]
class TaskDiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
        return oldItem == newItem
    }
}
package com.senai.taskapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.senai.taskapp.viewmodel.TaskViewModel
import com.senai.taskapp.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    // 1. Injeta o ViewModel, usando a Factory para passar o Repository [cite: 1677, 2180]
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referências aos elementos de layout
        val etTaskTitle: EditText = findViewById(R.id.etTaskTitle)
        val btnAddTask: Button = findViewById(R.id.btnAddTask)
        val rvTasks: RecyclerView = findViewById(R.id.rvTasks)

        // 2. Configura o Adapter
        val taskAdapter = TaskAdapter(
            onChecked = { task -> taskViewModel.update(task) }, // Update no status [cite: 1678]
            onDelete = { task -> taskViewModel.delete(task) } // Deleta a tarefa [cite: 1678]
        )

        rvTasks.adapter = taskAdapter

        // 3. Observa o LiveData (Flow do Room) para Reatividade [cite: 2181]
        taskViewModel.allTasks.observe(this) { tasks ->
            tasks?.let { taskAdapter.submitList(it) } // Atualiza o RecyclerView automaticamente [cite: 2182]
        }

        // 4. Listener do Botão Adicionar (operação de Insert)
        btnAddTask.setOnClickListener {
            val title = etTaskTitle.text.toString().trim()
            if (title.isNotEmpty()) {
                taskViewModel.insert(title)
                etTaskTitle.setText("") // Limpa o campo
            }
        }
    }
}
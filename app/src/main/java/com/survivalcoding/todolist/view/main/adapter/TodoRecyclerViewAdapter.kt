package com.survivalcoding.todolist.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.data.TodoRepository
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.main.holder.TodoViewHolder
import com.survivalcoding.todolist.view.main.model.TodoData


class TodoRecyclerViewAdapter(
    private var repository: TodoRepository,
    val deleteClickListener: (TodoData) -> (Unit),
    val editClickListener: (TodoData, String) -> (Unit),
    val checkBoxClickListener: (TodoData) -> (Unit),
) :
    ListAdapter<TodoData, TodoViewHolder>(TodoDiffCallback) {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        val view = binding.root
        val holder = TodoViewHolder(view, binding)
        view.tag = holder
        binding.buttonDelete.setOnClickListener { deleteClickListener.invoke(getItem(holder.adapterPosition)) }
        binding.buttonEdit.setOnClickListener {
            binding.editTodo.visibility = View.VISIBLE
            binding.textTodo.visibility = View.GONE
            binding.textDate.visibility = View.GONE
            binding.buttonSubmit.visibility = View.VISIBLE
            binding.buttonEdit.visibility = View.GONE
            binding.editTodo.setText(binding.textTodo.text.toString())
        }

        binding.buttonSubmit.setOnClickListener {
            editClickListener.invoke(
                getItem(holder.adapterPosition),
                binding.editTodo.text.toString()
            )

        }

        binding.checkBox.setOnClickListener {
            checkBoxClickListener.invoke(getItem(holder.adapterPosition))
        }

        return holder
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)

    }

}
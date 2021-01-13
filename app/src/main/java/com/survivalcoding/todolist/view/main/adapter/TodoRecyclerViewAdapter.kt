package com.survivalcoding.todolist.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.data.TodoLocalRepository
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.main.holder.TodoViewHolder
import com.survivalcoding.todolist.view.main.model.TodoData


class TodoRecyclerViewAdapter(
    private var repository: TodoLocalRepository,
    val deleteClickListener: (TodoData) -> (Unit),
    val editClickListener: (TodoData) -> (Unit),
) :
    ListAdapter<TodoData, TodoViewHolder>(TodoDiffCallback) {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        val view = binding.root
        val holder = TodoViewHolder(view, binding)
        view.tag = holder
        binding.buttonDelete.setOnClickListener { deleteClickListener.invoke(getItem(holder.adapterPosition)) }
        binding.buttonEdit.setOnClickListener { editClickListener.invoke(getItem(holder.adapterPosition)) }
        return holder
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)

    }

}
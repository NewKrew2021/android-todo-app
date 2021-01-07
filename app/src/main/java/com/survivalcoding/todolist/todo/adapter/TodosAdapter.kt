package com.survivalcoding.todolist.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding

data class Todos(val isDone: Boolean, val Title: String)

class RecyclerViewAdapter(private val data: List<Todos>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    lateinit var todoBinding: ItemTodoBinding

    class ViewHolder(binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        todoBinding = ItemTodoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(todoBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val settingValue = data[position]

        todoBinding.todoText.text = settingValue.Title
        todoBinding.isDoneButton.isChecked = settingValue.isDone
    }

    override fun getItemCount() = data.size
}
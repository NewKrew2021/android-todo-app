package com.survivalcoding.todolist.todo.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.todo.view.model.TodoItem

class RecyclerViewAdapter(private val data: MutableList<TodoItem>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    lateinit var todoBinding: ItemTodoBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        todoBinding =
            ItemTodoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(
            todoBinding,
            ::removeTodo
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindingHolder(data[position])
        holder.bindingListener(position)
    }

    fun removeTodo(position: Int) {
        data.removeAt(position)
        notifyDataSetChanged()
    }

    fun addTodo(todo: TodoItem) {
        data.add(todo)
        notifyDataSetChanged()
    }


    class ViewHolder(private val binding: ItemTodoBinding, val removeItem: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bindingHolder(data: TodoItem) {
            binding.todoText.text = data.title
            binding.isDoneButton.isChecked = data.isDone
        }

        fun bindingListener(pos: Int) {
            binding.isDoneButton.setOnClickListener { view ->
                binding.isDoneButton.isChecked = !binding.isDoneButton.isChecked
            }
            binding.deleteButton.setOnClickListener {
                removeItem.invoke(adapterPosition)
            }
        }
    }
}

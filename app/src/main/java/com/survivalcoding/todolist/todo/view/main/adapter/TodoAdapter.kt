package com.survivalcoding.todolist.todo.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.todo.data.TodoData
import com.survivalcoding.todolist.todo.view.model.Todo
import java.util.*

class TodoAdapter() : RecyclerView.Adapter<TodoAdapterViewHolder>() {
    lateinit var itemTodoBinding: ItemTodoBinding
    private val model = TodoData
    private val currentTime = Date().time

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoAdapterViewHolder {
        itemTodoBinding =
            ItemTodoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return TodoAdapterViewHolder(itemTodoBinding)
    }

    override fun getItemCount() = model.getDataSize()

    override fun onBindViewHolder(holder: TodoAdapterViewHolder, position: Int) {
        val todoList = model.todoList
        itemTodoBinding.apply {
            todoText.text = todoList[position].text
            isDoneButton.isChecked = todoList[position].isDone
            val dueDate = (todoList[position].dueDate - currentTime) / (24 * 60 * 60 * 1000)
            dueDateText.text = "D - $dueDate"

            deleteTodoButton.setOnClickListener {
                model.deleteTodo(todoList[position])
                notifyDataSetChanged()
            }
            isDoneButton.setOnClickListener {
                model.doneTodo(holder.adapterPosition)
                notifyDataSetChanged()
            }
        }
    }

    fun addTodo(todo: Todo) {
        model.addTodo(todo)
        notifyDataSetChanged()
    }

    fun sortByTitle(order: Int) {
        model.sortByTitle(order)
        notifyDataSetChanged()
    }

    fun sortByDueDate(order: Int) {
        model.sortByDate(order)
        notifyDataSetChanged()
    }
}

package com.survivalcoding.todolist.todo.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.todo.data.TodoData
import com.survivalcoding.todolist.todo.view.model.Todo
import java.util.*

class TodoAdapter() : ListAdapter<Todo, TodoAdapterViewHolder>(TodoDiffCallback) {
    lateinit var itemTodoBinding: ItemTodoBinding
    private val model = TodoData
    private val currentTime = Date().time

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoAdapterViewHolder {
        itemTodoBinding =
            ItemTodoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return TodoAdapterViewHolder(itemTodoBinding)
    }

    override fun onBindViewHolder(holder: TodoAdapterViewHolder, position: Int) {
        val todoList = model.todoList
        val holderPos = holder.adapterPosition

        itemTodoBinding.apply {
            todoText.text = todoList[holderPos].text
            isDoneButton.isChecked = todoList[holderPos].isDone
            val dueDate =
                (todoList[holderPos].dueDate - currentTime) / (ONE_DAY_MILLISECONDS)
            dueDateText.text = "D - $dueDate"

            deleteTodoButton.setOnClickListener {
                model.deleteTodo(todoList[holderPos])
                submitList(model.todoList.toList())
            }
            isDoneButton.setOnClickListener {
                model.doneTodo(holderPos)
                submitList(model.todoList.toList())
            }
        }
    }

    fun addTodo(todo: Todo) {
        model.addTodo(todo)
        submitList(model.todoList.toList())
    }

    fun sortByTitle(order: Int) {
        model.sortByTitle(order)
        submitList(model.todoList.toList())
    }

    fun sortByDueDate(order: Int) {
        model.sortByDate(order)
        submitList(model.todoList.toList())
    }

    companion object {
        const val ONE_DAY_MILLISECONDS = 24 * 60 * 60 * 1000 // 86_400_000
    }
}

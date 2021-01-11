package com.survivalcoding.todolist.todo.view.main.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.todo.data.TodoData
import com.survivalcoding.todolist.todo.view.main.MainActivity
import com.survivalcoding.todolist.todo.view.model.Todo
import java.util.*

class TodoAdapter(final val textClickEvent: () -> Unit) :
    ListAdapter<Todo, TodoAdapterViewHolder>(TodoDiffCallback) {
    lateinit var itemTodoBinding: ItemTodoBinding
    private val model = TodoData
    private val currentTime = Date().time - MainActivity.ONE_DAY_MILLISECONDS

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoAdapterViewHolder {
        itemTodoBinding =
            ItemTodoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return TodoAdapterViewHolder(itemTodoBinding)
    }

    override fun onBindViewHolder(holder: TodoAdapterViewHolder, position: Int) {
        val todoList = model.todoList

        itemTodoBinding.apply {
            todoText.text = todoList[holder.adapterPosition].text
            isDoneButton.isChecked = todoList[holder.adapterPosition].isDone
            val dueDate =
                (todoList[holder.adapterPosition].dueDate - currentTime) / MainActivity.ONE_DAY_MILLISECONDS
            dueDateText.text = "D - $dueDate"

            deleteTodoButton.setOnClickListener {
                model.deleteTodo(todoList[holder.adapterPosition])
                submitList(model.todoList.toList())
            }
            isDoneButton.setOnClickListener {
                model.doneTodo(holder.adapterPosition)
                drawCancelLine(this, todoList[holder.adapterPosition].isDone)
            }
            todoText.setOnClickListener { _ ->
                textClickEvent.invoke()
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

    fun updateTodo(item: ArrayList<Todo>) {
        model.updateTodo(item)
        submitList(model.todoList.toList())
    }

    private fun drawCancelLine(binding: ItemTodoBinding, isDone: Boolean) {
        if (isDone) {
            binding.todoText.apply {
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                setTextColor(Color.GRAY)
            }
        } else {
            binding.todoText.apply {
                paintFlags = 0
                setTextColor(Color.BLACK)
            }
        }
        binding.isDoneButton.isChecked = isDone
    }
}

package com.survivalcoding.todolist.todo.view.main.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.todo.view.MainActivity
import com.survivalcoding.todolist.todo.view.model.Todo
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapter(
    private val deleteOnClick: (Todo) -> Unit,
    private val textOnClick: (Todo) -> Unit,
    private val update: (Unit) -> Unit,
    private val isDoneUpdate: (Todo) -> Unit,
) : ListAdapter<Todo, TodoAdapterViewHolder>(TodoDiffCallback) {

    lateinit var itemTodoBinding: ItemTodoBinding
    private val currentTime = Date().time

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoAdapterViewHolder {
        itemTodoBinding =
            ItemTodoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return TodoAdapterViewHolder(itemTodoBinding)
    }

    override fun onBindViewHolder(holder: TodoAdapterViewHolder, position: Int) {
        val todo = getItem(holder.adapterPosition)
        val dateFormat = SimpleDateFormat(MainActivity.TIME_FORMAT, Locale.getDefault())

        itemTodoBinding.apply {
            todoText.text = todo.text
            drawCancelLine(this, todo.isDone)
            isDoneButton.isChecked = todo.isDone
            val dueDate = (todo.dueDate - currentTime) / MainActivity.ONE_DAY_MILLISECONDS
            dueDateText.text = "D - $dueDate"
            todoWriteDate.text = dateFormat.format(todo.writeTime)

            deleteTodoButton.setOnClickListener {
                deleteOnClick.invoke(todo)
                update.invoke(Unit)
            }
            isDoneButton.setOnClickListener {
                todo.isDone = !todo.isDone
                drawCancelLine(this, todo.isDone)
                isDoneUpdate.invoke(todo)
                update.invoke(Unit)
            }
            todoTextLayout.setOnClickListener {
                textOnClick.invoke(todo)
            }
        }
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

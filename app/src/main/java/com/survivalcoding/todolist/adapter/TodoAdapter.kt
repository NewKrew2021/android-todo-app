package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.util.convertToDate

class TodoAdapter(private var todoList: MutableList<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var listener: ((todoItem: TodoItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(inflater, parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bindView(todoList[position])
        holder.checkBoxEventProcess(todoList[position]) { sortTodoItem() }
        holder.removeEventProcess { removeTodoItem(position) }
        holder.modifyEventProcess(todoList[position], listener!!, { removeTodoItem((position)) })
    }

    override fun getItemCount() = todoList.size

    fun addTodoItem(todoItem: TodoItem) {
        todoList.add(todoItem)
        sortTodoItem()
    }

    private fun sortTodoItem() {
        todoList = todoList.sortedWith(compareBy(
            { if (it.complete) 1 else 0 },
            { -it.time }
        )).toMutableList()
        notifyDataSetChanged()
    }

    private fun removeTodoItem(position: Int) {
        todoList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, todoList.size)
    }

    fun setModifyTodoItemListener(listener : (todoItem : TodoItem) -> Unit){
        this.listener = listener
    }

    class TodoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(todoItem: TodoItem) {
            binding.apply {
                cbCompleteTodo.isChecked = todoItem.complete
                tvTimeTodo.text = convertToDate(todoItem.time)
                tvContentsTodo.text = todoItem.contents
            }
        }

        fun checkBoxEventProcess(todoItem: TodoItem, sort: () -> Unit) {
            binding.cbCompleteTodo.setOnClickListener {
                if (binding.cbCompleteTodo.isChecked) todoItem.complete = !todoItem.complete
                sort.invoke()
            }
        }

        fun removeEventProcess(remove: () -> Unit) {
            binding.ivRemoveTodo.setOnClickListener {
                remove.invoke()
            }
        }

        fun modifyEventProcess(todoItem: TodoItem, listener: (todoItem: TodoItem) -> Unit, remove: () -> Unit){
            binding.ivModifyTodo.setOnClickListener {
                listener.invoke(todoItem)
                remove.invoke()
            }
        }

    }
}


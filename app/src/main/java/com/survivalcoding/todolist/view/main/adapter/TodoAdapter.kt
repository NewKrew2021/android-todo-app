package com.survivalcoding.todolist.view.main.adapter

import android.view.ActionMode
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.databinding.ItemTodoListBinding
import com.survivalcoding.todolist.view.main.holder.TodoViewHolder
import com.survivalcoding.todolist.view.main.model.Todo

class TodoAdapter(
    private val showToastMessageListener: (String) -> Unit,
    private val removeClickListener: (Todo) -> Unit,
    private val checkChangeListener: (Todo) -> Unit,
    private val updateListener: () -> Unit,
    private val editClickListener: (Todo) -> Unit,
    private val getActionMode: () -> ActionMode?,
    private val setActionBarTitle: (Todo) -> Unit,
) : ListAdapter<Todo, TodoViewHolder>(TodoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding =
            ItemTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(
            getItem(holder.adapterPosition),
            showToastMessageListener = showToastMessageListener,
            removeClickListener = removeClickListener,
            checkChangeListener = checkChangeListener,
            updateListener = updateListener,
            editClickListener = editClickListener,
            getActionMode = getActionMode,
            setActionBarTitle = setActionBarTitle,
        )
    }
}
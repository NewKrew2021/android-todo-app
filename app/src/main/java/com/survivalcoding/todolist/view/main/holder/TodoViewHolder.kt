package com.survivalcoding.todolist.view.main.holder

import android.graphics.Paint
import android.view.ActionMode
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ItemTodoListBinding
import com.survivalcoding.todolist.util.timesToString
import com.survivalcoding.todolist.view.main.model.Todo

class TodoViewHolder(private val binding: ItemTodoListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        todo: Todo,
        showToastMessageListener: (String) -> Unit,
        removeClickListener: (Todo) -> Unit,
        checkChangeListener: (Todo) -> Unit,
        updateListener: () -> Unit,
        editClickListener: (Todo) -> Unit,
        getActionMode: () -> ActionMode?,
        setActionBarTitle: (Todo) -> Unit,
    ) {
        with(binding) {
            textViewTitle.text = todo.title
            textViewTimes.text = timesToString(todo.times)
            checkBox.isChecked = todo.isDone

            updateViews(getActionMode, todo)
            updateTextPaintFlags(todo.isDone)

            checkBox.setOnClickListener {
                todo.isDone = checkBox.isChecked
                updateViews(getActionMode, todo)
                updateTextPaintFlags(todo.isDone)

                checkChangeListener.invoke(todo)
                updateListener.invoke()
            }

            layoutItem.setOnClickListener {
                if (getActionMode() == null) {
                    if (todo.isOption) {
                        todo.isOption = false
                        updateViews(getActionMode, todo)
                    }
                } else {
                    todo.isRemovable = !todo.isRemovable
                    layoutItem.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            if (todo.isRemovable) R.color.teal_200 else R.color.white
                        )
                    )
                    setActionBarTitle.invoke(todo)
                }
            }

            buttonMenus.setOnClickListener {
                todo.isOption = true
                updateViews(getActionMode, todo)
            }

            buttonEdit.setOnClickListener {
                todo.isOption = false
                editClickListener.invoke(todo)

                updateViews(getActionMode, todo)
            }

            buttonDelete.setOnClickListener {
                removeClickListener.invoke(todo)
                updateListener.invoke()

                showToastMessageListener.invoke(
                    root.context.getString(
                        R.string.fragment_main_message_after_todo_remove,
                        todo.title
                    )
                )
            }
        }
    }

    private fun updateViews(getActionMode: () -> ActionMode?, todo: Todo) {
        val actionMode = getActionMode.invoke()

        with(binding) {
            if (actionMode == null) {
                buttonMenus.visibility = if (todo.isOption) View.INVISIBLE else View.VISIBLE
                buttonEdit.visibility =
                    if (todo.isOption && !todo.isDone) View.VISIBLE else View.INVISIBLE
                buttonDelete.visibility = if (todo.isOption) View.VISIBLE else View.INVISIBLE
                checkBox.isEnabled = true
                layoutItem.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
            } else {
                buttonMenus.visibility = View.INVISIBLE
                buttonEdit.visibility = View.INVISIBLE
                buttonDelete.visibility = View.INVISIBLE
                checkBox.isEnabled = false
                layoutItem.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        if (todo.isRemovable) R.color.teal_200 else R.color.white
                    )
                )
            }
        }
    }

    private fun updateTextPaintFlags(isDone: Boolean) {
        with(binding) {
            textViewTitle.paintFlags =
                if (isDone) (textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                else (textViewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()) // Paint.STRIKE_THRU_TEXT_FLAG(0x10) 만 제거하기 위한 코드
        }
    }
}
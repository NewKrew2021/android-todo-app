package com.survivalcoding.todolist.view.main.holder

import android.graphics.Paint
import android.view.View
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ItemTodoListBinding
import com.survivalcoding.todolist.view.main.model.Todo

class TodoViewHolder(private val binding: ItemTodoListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        todo: Todo,
        showToastMessageListener: (String) -> Unit,
        removeClickListener: (Todo) -> Unit,
        updateListener: () -> Unit,
        editClickListener: (Todo) -> Unit,
        getActionMode: () -> ActionMode?,
        setActionBarTitle: () -> Unit,
    ) {
        binding.apply {
            textViewTitle.text = todo.title
            textViewTimes.text = todo.times
            checkBox.isChecked = todo.isDone

            updateViews(getActionMode, todo.isOption, todo.isDone)
            updateTextPaintFlags(todo.isDone)

            checkBox.setOnClickListener {
                todo.isDone = checkBox.isChecked
                updateViews(getActionMode, todo.isOption, todo.isDone)
                updateTextPaintFlags(todo.isDone)
                updateListener.invoke()
            }

            layoutItem.setOnClickListener {
                if (getActionMode() == null) {
                    if (todo.isOption) {
                        todo.isOption = false
                        updateViews(getActionMode, todo.isOption, todo.isDone)
                    }
                }
                else {
                    todo.isRemovable = !todo.isRemovable
                    layoutItem.setBackgroundColor(ContextCompat.getColor(binding.root.context,
                        if (todo.isRemovable) R.color.teal_200 else R.color.white))

                    setActionBarTitle.invoke()
                }
            }

            buttonMenus.setOnClickListener {
                todo.isOption = true
                updateViews(getActionMode, todo.isOption, todo.isDone)
            }

            buttonEdit.setOnClickListener {
                todo.isOption = false
                editClickListener.invoke(todo)

                updateViews(getActionMode, todo.isOption, todo.isDone)
            }

            buttonDelete.setOnClickListener {
                removeClickListener.invoke(todo)
                updateListener.invoke()

                showToastMessageListener.invoke("${todo.title} 삭제되었습니다.")
            }
        }
    }

    private fun updateViews(getActionMode: () -> ActionMode?, isOption: Boolean, isDone: Boolean) {
        val actionMode = getActionMode.invoke()

        if (actionMode == null) {
            binding.apply {
                buttonMenus.visibility = if (isOption) View.INVISIBLE else View.VISIBLE
                buttonEdit.visibility = if (isOption && !isDone) View.VISIBLE else View.INVISIBLE
                buttonDelete.visibility = if (isOption) View.VISIBLE else View.INVISIBLE
                checkBox.isEnabled = true
                layoutItem.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.white))
            }
        }
        else {
            binding.apply {
                buttonMenus.visibility = View.INVISIBLE
                buttonEdit.visibility = View.INVISIBLE
                buttonDelete.visibility = View.INVISIBLE
                checkBox.isEnabled = false
            }
        }
    }

    private fun updateTextPaintFlags(isDone: Boolean) {
        binding.apply {
            textViewTitle.paintFlags =
                if (isDone) (textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                else (textViewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()) // Paint.STRIKE_THRU_TEXT_FLAG(0x10) 만 제거하기 위한 코드
        }
    }
}
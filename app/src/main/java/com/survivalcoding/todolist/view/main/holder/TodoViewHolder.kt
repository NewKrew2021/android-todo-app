package com.survivalcoding.todolist.view.main.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
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
    ) {
        binding.apply {
            textViewTitle.text = todo.title
            textViewTimes.text = todo.times
            checkBox.isChecked = todo.isDone

            updateButtonsVisibility(todo.isOption)

            checkBox.setOnClickListener {
                todo.isDone = checkBox.isChecked
                updateListener.invoke()
            }

            layoutItem.setOnClickListener {
                if (todo.isOption) {
                    todo.isOption = false
                    updateButtonsVisibility(todo.isOption)
                }
            }

            buttonMenus.setOnClickListener {
                todo.isOption = true
                updateButtonsVisibility(todo.isOption)
            }

            buttonEdit.setOnClickListener {
                todo.isOption = false
                editClickListener.invoke(todo)
            }

            buttonDelete.setOnClickListener {
                removeClickListener.invoke(todo)
                updateListener.invoke()

                showToastMessageListener.invoke("${todo.title} 삭제되었습니다.")
            }
        }
    }

    private fun updateButtonsVisibility(isOption: Boolean) {
        binding.apply {
            buttonMenus.visibility = if (isOption) View.INVISIBLE else View.VISIBLE
            buttonEdit.visibility = if (isOption) View.VISIBLE else View.INVISIBLE
            buttonDelete.visibility = if (isOption) View.VISIBLE else View.INVISIBLE
        }
    }
}
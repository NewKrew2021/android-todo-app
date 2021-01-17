package com.survivalcoding.todolist.view.main.holder

import android.graphics.Color
import android.graphics.Paint
import android.util.TypedValue
import android.view.ActionMode
import android.view.View
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
        itemUpdateListener: (Todo) -> Unit,
        updateUIListener: () -> Unit,
        editClickListener: (Todo) -> Unit,
        getActionMode: () -> ActionMode?,
        setActionBarTitle: () -> Unit,
    ) {
        with(binding) {
            textViewTitle.text = todo.title
            textViewTimes.text = timesToString(todo.times)
            checkBox.isChecked = todo.isDone

            updateViews(getActionMode, todo)

            checkBox.setOnClickListener {
                todo.isDone = checkBox.isChecked
                updateViews(getActionMode, todo)

                itemUpdateListener.invoke(todo)
                updateUIListener.invoke()
            }

            layoutItem.setOnClickListener {
                if (getActionMode() == null) {
                    if (todo.isOption) {
                        todo.isOption = false
                        updateViews(getActionMode, todo)
                    }
                } else {
                    todo.isRemovable = !todo.isRemovable
                    updateViews(getActionMode, todo)

                    itemUpdateListener.invoke(todo)
                    setActionBarTitle.invoke()
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
                updateUIListener.invoke()

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

        updateButtonsVisibility(actionMode, todo)
        updateTextViews(actionMode, todo)
        updateCheckBoxEnable(actionMode)
        updateLayoutView(actionMode, todo)
    }

    private fun updateButtonsVisibility(actionMode: ActionMode?, todo: Todo) {
        with(binding) {
            if (actionMode == null) {
                buttonMenus.visibility = if (todo.isOption) View.INVISIBLE else View.VISIBLE
                buttonEdit.visibility = if (todo.isEditable()) View.VISIBLE else View.INVISIBLE
                buttonDelete.visibility = if (todo.isOption) View.VISIBLE else View.INVISIBLE
            } else {
                buttonMenus.visibility = View.INVISIBLE
                buttonEdit.visibility = View.INVISIBLE
                buttonDelete.visibility = View.INVISIBLE
            }
        }
    }

    private fun updateTextViews(actionMode: ActionMode?, todo: Todo) {
        with(binding) {
            textViewTitle.paintFlags =
                if (todo.isDone) (textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                else (textViewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()) // Paint.STRIKE_THRU_TEXT_FLAG(0x10) 만 제거하기 위한 코드

            if (actionMode == null) {
                textViewTitle.setTextColor(Color.BLACK)
                textViewTimes.setTextColor(Color.BLACK)
            } else {
                textViewTitle.setTextColor(if (todo.isRemovable) Color.WHITE else Color.BLACK)
                textViewTimes.setTextColor(if (todo.isRemovable) Color.WHITE else Color.BLACK)
            }
        }
    }

    private fun updateCheckBoxEnable(actionMode: ActionMode?) {
        binding.checkBox.isEnabled = actionMode != null
    }

    private fun updateLayoutView(actionMode: ActionMode?, todo: Todo) {
        with(binding) {
            val typedValue = TypedValue().apply {
                root.context.theme.resolveAttribute(
                    android.R.attr.selectableItemBackground,
                    this,
                    true
                )
            }

            if (actionMode == null) {
                layoutItem.setBackgroundResource(typedValue.resourceId)
            } else {
                layoutItem.setBackgroundResource(if (todo.isRemovable) R.drawable.gradation_todo_removable else typedValue.resourceId)
            }
        }
    }
}
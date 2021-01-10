package com.survivalcoding.todolist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ToDoListBinding
import com.survivalcoding.todolist.model.TodoItem

class TodoListAdapter(
    private val checkTodoListener: () -> Unit,
    private val editTodoListener: (TodoItem, String) -> Unit,
    private val removeTodoListener: (TodoItem) -> Unit
) :
    ListAdapter<TodoItem, TodoListAdapter.TodoViewHolder>(TodoDiffUtilCallback()) {
    private lateinit var binding: ToDoListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = ToDoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding, checkTodoListener, editTodoListener, removeTodoListener)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TodoViewHolder(
        private val binding: ToDoListBinding,
        private val checkTodoListener: () -> Unit,
        private val editTodoListener: (TodoItem, String) -> Unit,
        private val removeTodoListener: (TodoItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: TodoItem) {
            binding.apply {
                checkBox.isChecked = todoItem.isChecked
                toDoTitle.text = todoItem.todoTitle
                currentTime.text = todoItem.timeStamp
                setOnClickListener(todoItem)
                drawCancelLine(todoItem, todoItem.isChecked)
                showEditButton(!todoItem.isChecked)
            }
        }

        private fun setOnClickListener(item: TodoItem) {
            binding.apply {
                checkBox.setOnClickListener {
                    item.isChecked = checkBox.isChecked
                    drawCancelLine(item, item.isChecked)
                    showEditButton(!checkBox.isChecked)
                    checkTodoListener()
                }
                toDoTitle.setOnClickListener {
                    checkBox.isChecked.apply {
                        item.isChecked = !this
                        checkBox.isChecked = !this
                        showEditButton(this)
                        drawCancelLine(item, !this)
                    }
                    checkTodoListener()
                }
                editButton.setOnClickListener {
                    setLayoutVisibility(true)
                    editTodoTitle.setText(toDoTitle.text.toString())
                }
                removeButton.setOnClickListener {
                    removeTodoListener(item)
                }
                editApplyButton.setOnClickListener {
                    setLayoutVisibility(false)
                    toDoTitle.text = editTodoTitle.text.toString()
                    currentTime.text = item.timeStamp
                    editTodoListener(item, editTodoTitle.text.toString())
                }
                editCancelButton.setOnClickListener {
                    setLayoutVisibility(false)
                }
            }
        }

        // 체크 되어있으면 취소선을 그리고 체크 해제되면 취소선을 지운다
        private fun drawCancelLine(item: TodoItem, isChecked: Boolean) {
            binding.toDoTitle.paintFlags = if (isChecked) Paint.STRIKE_THRU_TEXT_FLAG else 0
        }

        // 수정하기 모드인지 일반 모드인지 구분해서 레이아웃 출
        private fun setLayoutVisibility(canEditTitle: Boolean) {
            binding.apply {
                if (canEditTitle) {
                    normalTodoLayout.visibility = View.INVISIBLE
                    editTodoLayout.visibility = View.VISIBLE
                } else {
                    normalTodoLayout.visibility = View.VISIBLE
                    editTodoLayout.visibility = View.INVISIBLE
                }
            }
        }

        private fun showEditButton(isEditable: Boolean) {
            binding.editButton.visibility = if (isEditable) View.VISIBLE else View.INVISIBLE
        }
    }
}

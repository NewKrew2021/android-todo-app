package com.survivalcoding.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoListBinding
import com.survivalcoding.todolist.model.Todo

// TODO : isDone, Times 에 따른 정렬 구현

class TodoAdapter(
    private val context: Context,
    private val items: MutableList<Todo>,
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodoListBinding.inflate(LayoutInflater.from(context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position, ::remove)
    }

    override fun getItemCount(): Int = items.size

    private fun remove(position: Int, title: String) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size - position)

        Toast.makeText(context, "$title 삭제되었습니다.", Toast.LENGTH_SHORT).show()
    }

    class ViewHolder(private val binding: ItemTodoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo, position: Int, remove: (_position: Int, text: String) -> Unit) {
            binding.apply {
                textViewTitle.text = todo.title
                textViewTimes.text = todo.times
                checkBox.isChecked = todo.isDone

                updateButtonsVisibility(todo.isOption)

                checkBox.setOnClickListener {
                    todo.isDone = checkBox.isChecked
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
                    // TODO : todo 제목 수정 구현
                }

                buttonDelete.setOnClickListener {
                    remove.invoke(position, textViewTitle.text.toString())
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
}
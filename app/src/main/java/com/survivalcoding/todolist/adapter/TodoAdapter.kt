package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoListBinding
import com.survivalcoding.todolist.model.Todo
import com.survivalcoding.todolist.util.stringToDate

// TODO : isDone, Times 에 따른 정렬 구현
class TodoAdapter(
    private val items: MutableList<Todo>,
    private val showToastMessage: (String) -> Unit,
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            items[position],
            position,
            { _position -> remove(_position) },
            { sort() },
        )
    }

    override fun getItemCount(): Int = items.size

    private fun remove(position: Int) {
        val item = items[position]

        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size - position)

        showToastMessage(item.title)
    }

    // 아직 완료하지 않은 일들만 시간 순으로 내림차순 정렬
    private fun sort() {
        items.sortWith(
            compareBy {
                if (!it.isDone) -stringToDate(it.times).time
                else Long.MAX_VALUE
            }
        )
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemTodoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo, position: Int, remove: (Int) -> Unit, sort: () -> Unit) {
            binding.apply {
                textViewTitle.text = todo.title
                textViewTimes.text = todo.times
                checkBox.isChecked = todo.isDone

                updateButtonsVisibility(todo.isOption)

                checkBox.setOnClickListener {
                    todo.isDone = checkBox.isChecked
                    sort()
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
                    remove(position)
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
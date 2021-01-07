package com.survivalcoding.todolist.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoListBinding
import com.survivalcoding.todolist.model.Todo
import com.survivalcoding.todolist.util.stringToDate
import com.survivalcoding.todolist.view.MainActivity

// TODO : isDone, Times 에 따른 정렬 구현
class TodoAdapter(
    private val items: MutableList<Todo>,
    private val showToastMessage: (String) -> Unit,
    private val startEditActivity: (Bundle) -> Unit,
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            items[position],
            { item -> remove(item) },
            { sort() },
            { args -> startEditActivity(args) },
        )
    }

    override fun getItemCount(): Int = items.size

    private fun remove(item: Todo) {
        val position = items.indexOf(item)

        items.remove(item)
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

    fun modify(item: Todo, title: String, times: String) {
        val position = items.indexOf(item)

        items[position].apply {
            this.title = title
            this.times = times
        }
        sort()
    }

    class ViewHolder(private val binding: ItemTodoListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo, remove: (Todo) -> Unit, sort: () -> Unit, startEditActivity: (Bundle) -> Unit) {
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
                    val args = Bundle()
                    args.putParcelable(MainActivity.TODO_ITEM_KEY, todo)

                    startEditActivity(args)
                }

                buttonDelete.setOnClickListener {
                    remove(todo)
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
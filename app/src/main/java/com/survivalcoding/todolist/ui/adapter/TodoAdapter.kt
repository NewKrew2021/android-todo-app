package com.survivalcoding.todolist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.util.convertToDate

class TodoAdapter :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(), Filterable {

    private var todoList: MutableList<TodoItem> = ArrayList()
    private var filterList: MutableList<TodoItem> = todoList

    private lateinit var modifyListener: ((todoItem: TodoItem) -> Unit)
    private lateinit var sortListener: (() -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(inflater, parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bindView(filterList[position])
        holder.checkBoxEventProcess(filterList[position], sortListener, { afterSortTodoItem() })
        holder.removeEventProcess { removeTodoItem(position) }
        holder.modifyEventProcess(
            filterList[position],
            modifyListener,
            { removeTodoItem((position)) })
    }

    override fun getItemCount() = filterList.size

    fun setTodoList(list: List<TodoItem>) {
        this.todoList = list as MutableList<TodoItem>
        filterList = todoList
        notifyDataSetChanged()
    }

    private fun afterSortTodoItem() {
        filterList = todoList
        notifyDataSetChanged()
    }

    private fun removeTodoItem(position: Int) {
        todoList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, todoList.size)
    }

    fun setModifyTodoItemListener(listener: (todoItem: TodoItem) -> Unit) {
        this.modifyListener = listener
    }

    fun setSortTodoItemListener(listener: () -> Unit) {
        this.sortListener = listener
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterString = constraint.toString()
            filterList = if (filterString == "") {
                todoList
            } else {
                val filteringList = mutableListOf<TodoItem>()
                todoList.filter {
                    it.contents?.toUpperCase()?.contains((filterString.toUpperCase())) ?: false
                }.forEach {
                    filteringList.add(it)
                }
                filteringList
            }

            val filterResult = FilterResults()
            filterResult.values = filterList
            return filterResult
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null) {
                filterList = results.values as MutableList<TodoItem>
                notifyDataSetChanged()
            }
        }
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

        fun checkBoxEventProcess(todoItem: TodoItem, listener: () -> Unit, afterSort: () -> Unit) {
            binding.cbCompleteTodo.setOnClickListener {
                if (binding.cbCompleteTodo.isChecked) todoItem.complete = !todoItem.complete
                listener.invoke()
                afterSort.invoke()
            }
        }

        fun removeEventProcess(remove: () -> Unit) {
            binding.ivRemoveTodo.setOnClickListener {
                remove.invoke()
            }
        }

        fun modifyEventProcess(
            todoItem: TodoItem,
            listener: (todoItem: TodoItem) -> Unit,
            remove: () -> Unit
        ) {
            binding.ivModifyTodo.setOnClickListener {
                listener.invoke(todoItem)
                remove.invoke()
            }
        }


    }

}


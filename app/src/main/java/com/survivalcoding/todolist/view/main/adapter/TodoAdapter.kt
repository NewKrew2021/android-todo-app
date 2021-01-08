package com.survivalcoding.todolist.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemTodoListBinding
import com.survivalcoding.todolist.util.stringToDate
import com.survivalcoding.todolist.view.main.holder.TodoViewHolder
import com.survivalcoding.todolist.view.main.model.Todo

class TodoAdapter(
    private val showToastMessage: (String) -> Unit,
    private val editClickEvent: (Todo) -> Unit,
) : RecyclerView.Adapter<TodoViewHolder>() {

    private val _items = mutableListOf<Todo>()
    val items: List<Todo>
        get() = _items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding =
            ItemTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(
            _items[position],
            remove = { _position -> remove(_position) },
            sort = { sort() },
            editClickEvent = { item -> editClickEvent(item) },
            updateUI = { notifyDataSetChanged() },
        )
    }

    override fun getItemCount(): Int = _items.size

    private fun remove(position: Int) {
        val item = _items[position]

        _items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, _items.size - position)

        showToastMessage("${item.title} 삭제되었습니다.")
    }

    // 아직 완료하지 않은 일들만 시간 순으로 내림차순 정렬
    private fun sort() {
        _items.sortWith(
            compareBy {
                if (!it.isDone) -stringToDate(it.times).time
                else Long.MAX_VALUE
            }
        )
    }

    fun add(item: Todo) {
        _items.add(0, item)
    }

    fun addAll(newItems: List<Todo>) {
        _items.clear()
        _items.addAll(newItems)
    }

    fun edit(item: Todo, title: String, times: String) {
        val position = _items.indexOf(item)

        try {
            with(_items[position]) {
                this.title = title
                this.times = times
                this.isOption = false
            }
            sort()
            notifyDataSetChanged()
        } catch (e: ArrayIndexOutOfBoundsException) {
            showToastMessage("일시적인 오류로 인해 수정할 수 없습니다.")
        }
    }
}
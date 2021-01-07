package com.survivalcoding.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.TodoData

class TodoRecyclerViewAdapter() :
    RecyclerView.Adapter<TodoRecyclerViewHolder>() {

    private var items = mutableListOf<TodoData>()
    lateinit var holder: TodoRecyclerViewHolder
    private fun sortItem(target: Int) {
        //To-Do 아이템 sorting (완료 -> 즐겨찾기 -> 시간 순으로)
        val fromPosition = items.map { it.pid }.indexOf(target)
        items = items.sortedWith(compareBy(
            { if (it.isChecked) 1 else 0 },
            { if (it.isMarked) 0 else 1 },
            { -it.time }
        )).toMutableList()
        val toPosition = items.map { it.pid }.indexOf(target)
        //이동 애니메이션 적용
        notifyItemMoved(fromPosition, toPosition)

    }

    fun addItem(data: TodoData) {
        //To-Do 아이템 추가
        items.add(0, data)
        //추가 애니메이션 적용
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoRecyclerViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        val view = binding.root
        holder = TodoRecyclerViewHolder(view, binding)
        view.tag = holder
        return holder
    }

    override fun onBindViewHolder(viewHolder: TodoRecyclerViewHolder, position: Int) {

        val item = items[position]
        holder.bind(item, ::sortItem)

    }

    override fun getItemCount() = items.size

}
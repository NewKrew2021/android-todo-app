package com.survivalcoding.todolist.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.survivalcoding.todolist.databinding.ListItemBinding
import com.survivalcoding.todolist.view.main.model.TodoData
import com.survivalcoding.todolist.viewmodel.TodoRepository
import kotlin.reflect.KFunction1


class TodoRecyclerViewAdapter(
    private var model: TodoRepository,
    val itemClickListener: KFunction1<TodoData, Unit>
) :
    ListAdapter<TodoData, TodoRecyclerViewHolder>(TodoDiffCallback) {

    lateinit var holder: TodoRecyclerViewHolder
    val items: List<TodoData>
        get() = model.items

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoRecyclerViewHolder {
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        val view = binding.root
        holder = TodoRecyclerViewHolder(view, binding)
        view.tag = holder
        return holder
    }

    override fun onBindViewHolder(viewHolder: TodoRecyclerViewHolder, position: Int) {

        val item = model.getItem(position)
        holder.bind(
            item,
            sortFunction = { model.sortItem() },
            delFunction = { model.delItem(item) },
            submitFunction = { updateUI() },
            itemClickListener = { itemClickListener(item) },
        )

    }

    override fun getItemCount() = model.getSize()

    fun addAllItems(data: List<TodoData>) {
        model.addAllItems(data)
        updateUI()
    }

    fun addItem(data: TodoData) {
        model.addItem(data)
        updateUI()
    }

    private fun updateUI() {
        submitList(model.items.toList())
        //todo updateUI가 edit시에 잘 작동하지않음
    }


    fun changeEditable(isEdit: Boolean) {
        model.makeEditable(isEdit)
        //todo updateUI가 작동 안함
        notifyItemRangeChanged(0, model.items.size)
    }

}
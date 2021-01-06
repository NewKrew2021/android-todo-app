package com.survivalcoding.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemBinding

class MyAdapterRecycler(val itemClick: (MyAdapterRecycler,Int) -> Unit) :
    RecyclerView.Adapter<Holder>() {

    var data = mutableListOf<listItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(itemBinding, itemClick, data, this)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.setData(data[position])

        holder.itemClickListener(position)


    }
}

class Holder(
    val binding: ItemBinding,
    val itemClick: (MyAdapterRecycler,Int) -> Unit,
    var data: MutableList<listItem>,
    var adapter: MyAdapterRecycler
) :
    RecyclerView.ViewHolder(binding.root) {


    fun setData(data: listItem) {
        binding.checkBox.text = data.toDo
        binding.textView.text = data.time

    }

    fun itemClickListener(position : Int) {
        binding.button.setOnClickListener {
            itemClick(adapter,position)
        }
    }

}
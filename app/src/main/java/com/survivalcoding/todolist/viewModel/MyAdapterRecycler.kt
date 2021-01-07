package com.survivalcoding.todolist.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemBinding

class MyAdapterRecycler(val itemClick: (MyAdapterRecycler, Int) -> Unit) :
    RecyclerView.Adapter<Holder>() {

    var data = mutableListOf<listItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(
            itemBinding,
            itemClick
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.setData(data[position])

        //Log.d("로그" ,"$position")
        holder.itemClickListener(position, this)
    }
}

class Holder(
    val binding: ItemBinding,
    val itemClick: (MyAdapterRecycler, Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {


    fun setData(data: listItem) {
        binding.checkBox.text = data.toDo
        binding.textView.text = data.time
    }

    fun itemClickListener(position: Int, adapter: MyAdapterRecycler) {
        binding.button.setOnClickListener {
            itemClick(adapter, position)
        }
    }
}
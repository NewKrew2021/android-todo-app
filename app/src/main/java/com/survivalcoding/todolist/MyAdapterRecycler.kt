package com.survivalcoding.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemBinding

class MyAdapterRecycler : RecyclerView.Adapter<Holder>() {

    var data = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(itemBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val tmp = data[position]
        holder.setData(tmp)
    }

}

class Holder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun setData(data: String) {
        binding.checkBox.text = data
    }


}
package com.survivalcoding.todolist.view

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ItemBinding
import com.survivalcoding.todolist.viewModel.listItem
import com.survivalcoding.todolist.viewModel.searchItem

class RecyclerAdapter(val itemClick: (RecyclerAdapter, Int) -> Unit) :
    ListAdapter<searchItem, Holder>(ItemDiffCallback) {

    /*
    fun dataUpdate() {
        searchData.clear()
        for(i in 0..data.size-1){
            searchData.add(searchItem(data[i],i))
        }
        submitList(searchData)
    }
     */
    var searchData = mutableListOf<searchItem>()
    fun getSearchData(data: MutableList<searchItem>) {
        searchData = data
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(
            itemBinding,
            itemClick
        )
    }

    override fun getItemCount(): Int {
        return searchData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.setData(searchData[holder.adapterPosition].item)
        holder.checkBoxClickListener(searchData[holder.adapterPosition].item)
        holder.itemClickListener(holder.adapterPosition, this)
    }
}

class Holder(
    val binding: ItemBinding,
    val itemClick: (RecyclerAdapter, Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun setData(data: listItem) {
        binding.checkBox.text = data.toDo
        binding.textView.text = data.time
        binding.checkBox.isChecked = data.check
        if (data.complete == true) {
            binding.ConstraintLayout.setBackgroundColor(Color.RED)
            binding.checkBox.isClickable = false
            binding.button.isEnabled = false
        } else {
            binding.ConstraintLayout.setBackgroundColor(Color.WHITE)
            binding.checkBox.isClickable = true
            binding.button.isEnabled = true
        }
    }


    fun itemClickListener(position: Int, adapter: RecyclerAdapter) {
        binding.button.setOnClickListener {
            itemClick(adapter, position)
        }
    }

    fun checkBoxClickListener(data: listItem) {
        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) data.check = true
            else data.check = false
        }
    }
}
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

    var data = mutableListOf<listItem>()
    var searchData = mutableListOf<searchItem>()

    fun dataUpdate() {
        searchData = searchData.toMutableList()
        submitList(searchData)
    }

    fun searching(pattern: String) {

        makeSearchData(pattern)
        notifyDataSetChanged()
        //dataUpdate()
    }

    fun makeSearchData(pattern: String) {
        searchData.clear()

        for (i in 0..data.size - 1) {
            if (data[i].toDo.contains(pattern)) {
                searchData.add(searchItem(data[i], i))
            }
        }
    }

    fun checkedComplete(pattern: String) {

        checkingComplete(searchData)

        var last_index = data.size - 1
        var index = 0
        for (i in 0..last_index) {
            if (data[index].complete == true) {
                data.add(last_index + 1, data[index])
                data.removeAt(index)
            } else {
                index += 1
            }
        }
        makeSearchData(pattern)
    }

    private fun checkingComplete(dataList: MutableList<searchItem>) {
        var tmp_size = dataList.size
        var index = 0
        for (i in 0..tmp_size - 1) {
            if (dataList[index].item.check == true) {

                dataList[index].item.check = false
                dataList[index].item.complete = true
                data[dataList[index].index].complete = true

                dataList.add(
                    dataList[index]
                )
                dataList.removeAt(index)
                notifyItemRemoved(index)

            } else {
                index += 1
            }
        }
        notifyItemRangeChanged(0, dataList.size)
    }

    fun checkedRemove(pattern: String) {
        val tmp = mutableListOf<Int>()
        for (i in searchData.size - 1 downTo 0) {
            if (searchData[i].item.check == true) {
                tmp.add(searchData[i].index)
                notifyItemRemoved(i)
            }
        }
        tmp.sortBy { it }
        for (i in tmp.size - 1 downTo 0) {
            data.removeAt(tmp[i])
        }
        makeSearchData(pattern)
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
        binding.checkBox.isChecked = false
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
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
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.data.db.TodoSqliteRepository
import com.survivalcoding.todolist.databinding.ItemBinding
import com.survivalcoding.todolist.viewModel.listItem
import com.survivalcoding.todolist.viewModel.searchItem
import java.text.SimpleDateFormat

class RecyclerAdapter(
    val todoRepository: DefaultTodoRepository,
    val itemClick: (RecyclerAdapter, Int) -> Unit
) :
    ListAdapter<searchItem, Holder>(ItemDiffCallback) {


    var data = mutableListOf<listItem>()
    var searchData = mutableListOf<searchItem>()

    fun getData(data: MutableList<listItem>, searchData: MutableList<searchItem>) {
        this.searchData = searchData
        this.data = data
    }

    fun searching(pattern: String) {

        makeSearchData(pattern)
        notifyDataSetChanged()
        //adapter.submitList(null)
        //adapter.submitList(searchData)
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

    fun checkingComplete(dataList: MutableList<searchItem>) {
        var tmp_size = dataList.size
        var index = 0
        for (i in 0..tmp_size - 1) {
            if (dataList[index].item.check == true) {

                dataList[index].item.check = false
                dataList[index].item.complete = true
                data[dataList[index].index].complete = true
                if (todoRepository is TodoSqliteRepository) todoRepository.updateItem(data[dataList[index].index])

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
            if (todoRepository is TodoSqliteRepository) todoRepository.removeItem(data[tmp[i]].id)
            data.removeAt(tmp[i])
        }
        makeSearchData(pattern)
    }

    fun addItem(todo: String) {
        val sdf = SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
        val date = System.currentTimeMillis()
        val currentDate = sdf.format(date)

        if (todoRepository is TodoSqliteRepository) {
            todoRepository.maxId += 1

            data.add(
                0,
                listItem(
                    todo,
                    currentDate,
                    check = false,
                    complete = false,
                    id = todoRepository.maxId
                )
            )
            todoRepository.addItem(
                listItem(
                    todo,
                    currentDate,
                    check = false,
                    complete = false,
                    id = todoRepository.maxId
                )
            )
        } else {
            data.add(
                0,
                listItem(
                    todo,
                    currentDate,
                    check = false,
                    complete = false,
                    id = 0
                )
            )
        }


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
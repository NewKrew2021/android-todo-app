package com.survivalcoding.todolist.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.databinding.ItemBinding

class MyAdapterRecycler(val itemClick: (MyAdapterRecycler, Int) -> Unit) :
    RecyclerView.Adapter<Holder>() {

    var data = mutableListOf<listItem>()


    fun checkedRemove() {
        var tmp = mutableListOf<Int>()
        var i = 0
        while (i <= data.size - 1) {
            if (data[i].check == true) {
                data.removeAt(i)
                notifyItemRemoved(i)
            } else i += 1
        }

        notifyItemRangeChanged(0, data.size)

    }

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

        holder.itemClickListener(position, this)

        holder.checkBoxClickListener(data[position])

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
        binding.checkBox.isChecked = false
    }

    fun itemClickListener(position: Int, adapter: MyAdapterRecycler) {
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
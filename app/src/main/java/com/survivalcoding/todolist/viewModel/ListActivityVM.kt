package com.survivalcoding.todolist.viewModel

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityListBinding
import java.text.SimpleDateFormat

object ListActivityVM {

    fun addButtonListener(adapter: MyAdapterRecycler, binding: ActivityListBinding) {
        val sdf = java.text.SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
        val date = System.currentTimeMillis()
        val currentDate = sdf.format(date)

        adapter.data.add(
            0,
            listItem(
                binding.editText.text.toString(),
                currentDate,
                false
            )
        )
        adapter.notifyDataSetChanged()
        binding.editText.setText("")
    }

    fun dialogSetting(dialogView: View, adapter: MyAdapterRecycler, position: Int) {
        var reviseText = dialogView.findViewById<EditText>(R.id.reviseText)
        //bindingDialog.reviseText.text

        var tmpString = reviseText.text.toString()
        val sdf = SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
        val date = System.currentTimeMillis()
        val currentDate = sdf.format(date)
        //adapter.data[position].toDo=tmpString
        //adapter.data[position].time=currentDate
        //adapter.data[position]= listItem(tmpString,currentDate)
        adapter.data.add(
            0,
            listItem(
                tmpString,
                currentDate,
                false
            )
        )

        adapter.data.removeAt(position + 1)
        adapter.notifyItemRangeChanged(position, adapter.data.size)
        adapter.notifyDataSetChanged()
    }


    fun onSaveInstace(outState: Bundle, adapter: MyAdapterRecycler) {
        outState.putParcelableArrayList("data", adapter.data as ArrayList<listItem>)
    }

    fun onRestoreInstance(savedInstanceState: Bundle, adapter: MyAdapterRecycler) {
        val data = savedInstanceState.getParcelableArrayList<listItem>("data")
        data?.let {
            adapter.data = data
            adapter.notifyDataSetChanged()
        }
    }

}
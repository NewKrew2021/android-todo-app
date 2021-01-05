package com.survivalcoding.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyAdapter : BaseAdapter() {

    companion object {

        var data = mutableListOf<String>()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view : View
        if(convertView == null)
        {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.item,parent,false)

        }
        else
        {
            view = convertView
        }

        val todo_text = view.findViewById<TextView>(R.id.textView2)

        val currentData = getItem(position).toString()
        todo_text.text = currentData
        return view
    }

    override fun getItem(position: Int): Any {
       return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return data.size
    }


}
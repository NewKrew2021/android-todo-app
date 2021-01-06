package com.survivalcoding.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoItem
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var adapter: TodoListAdapter
    lateinit var binding: ActivityMainBinding

    private val sampleData = mutableListOf<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            addItem()
        }

        (1..5).forEach {
            sampleData.add(TodoItem(it.toString(), false))
        }

        adapter = TodoListAdapter(this, sampleData)

        binding.list.adapter = this.adapter
    }

    private fun addItem() {
        adapter.addItem(TodoItem((sampleData.size + 1).toString(), false))
    }

}
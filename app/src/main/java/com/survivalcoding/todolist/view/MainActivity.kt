package com.survivalcoding.todolist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.model.TodoItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeView()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.registerButton.setOnClickListener {
            binding.toDoEditText.text.apply {
                adapter.addNewTodo(TodoItem(false, toString()))
                clear()
            }
        }
    }

    private fun initializeView() {
        adapter = TodoListAdapter(mutableListOf())
        binding.toDoList.adapter = adapter
        binding.toDoList.apply {
            this.adapter = this@MainActivity.adapter
            addItemDecoration(
                DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
            )
        }
    }
}
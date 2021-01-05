package com.survivalcoding.todolist

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.survivalcoding.todolist.adapter.TodoItem
import com.survivalcoding.todolist.adapter.TodoListAdapter

class MainActivity : AppCompatActivity() {

    lateinit var addButton: FloatingActionButton
    lateinit var adapter: TodoListAdapter
    lateinit var todoList: ListView

    private val sampleData = mutableListOf<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.add)
        addButton.setOnClickListener {
            addItem()
        }

        todoList = findViewById(R.id.list)

        (1..5).forEach {
            sampleData.add(TodoItem(it.toString(), false))
        }

        adapter = TodoListAdapter(this, sampleData)

        todoList.adapter = adapter
    }

    private fun addItem() {
        adapter.addItem(TodoItem((sampleData.size + 1).toString(), false))
    }

}
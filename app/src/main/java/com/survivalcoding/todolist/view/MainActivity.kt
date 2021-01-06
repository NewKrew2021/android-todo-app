package com.survivalcoding.todolist.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.model.TodoItem

class MainActivity : AppCompatActivity() {
    private lateinit var todoEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var listView: ListView
    private lateinit var adapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeView()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        registerButton.setOnClickListener {
            adapter.addNewTodo(TodoItem(false, todoEditText.text.toString()))
            todoEditText.text.clear()
        }
    }

    private fun initializeView() {
        todoEditText = findViewById(R.id.to_do_edit_text)
        registerButton = findViewById(R.id.register_button)
        listView = findViewById(R.id.to_do_list)
        adapter = TodoListAdapter(mutableListOf())
        listView.adapter = adapter
    }
}
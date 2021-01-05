package com.survivalcoding.todolist

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoListView = findViewById<ListView>(R.id.todoListView)

        val todoListAdapter = TodoListAdapter()
        todoListView.adapter = todoListAdapter

        todoListView.setOnItemClickListener { parent, view, position, id -> Toast.makeText(this, "$position item click", Toast.LENGTH_SHORT).show() }
    }
}
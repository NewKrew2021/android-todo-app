package com.survivalcoding.todolist.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val todoListAdapter = TodoListAdapter()
        binding.todoListView.adapter = todoListAdapter

        binding.todoListView.setOnItemClickListener { _, _, position, _ -> Toast.makeText(this, "$position item click", Toast.LENGTH_SHORT).show() }

    }

}
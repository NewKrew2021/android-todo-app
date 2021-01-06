package com.survivalcoding.todolist.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.model.TodoItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStartView()
        eventProcess()
        afterStartView()

    }

    private fun initStartView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

    private fun afterStartView() {
        val todoList = mutableListOf(
            TodoItem("2020년 1월5일 17시47분", "첫 번째 할일", false),
            TodoItem("2020년 1월6일 13시32분", "두 번째 할일", false),
            TodoItem("2020년 1월7일 14시23분", "세 번째 할일", false),
        )

        val todoAdapter = TodoAdapter(todoList)
        binding.rvTodoMain.adapter = todoAdapter
    }

    private fun eventProcess() {
        binding.btnAddMain.setOnClickListener {
            Toast.makeText(applicationContext, "hi", Toast.LENGTH_SHORT).show()
        }
    }
}
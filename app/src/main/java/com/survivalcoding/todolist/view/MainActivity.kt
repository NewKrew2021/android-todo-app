package com.survivalcoding.todolist.view

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoAdapter
import com.survivalcoding.todolist.model.Todo

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        val todoList = mutableListOf(
                Todo("2020년 1월5일 17시47분", "첫 번째 할일", false),
                Todo("2020년 1월6일 13시32분", "두 번째 할일", false),
                Todo("2020년 1월7일 14시23분", "세 번째 할일", false),
        )

        val lvTodo = findViewById<ListView>(R.id.lv_todo_main)
        val todoAdapter = TodoAdapter(todoList)
        lvTodo.adapter = todoAdapter
    }

    fun addTodo(v: View) {
        Toast.makeText(this, "to do 추가!", Toast.LENGTH_SHORT).show()
    }
}
package com.survivalcoding.todolist

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TodoAdapter()

        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "$position 번째 아이템 선택", Toast.LENGTH_SHORT).show()
        }
    }
}
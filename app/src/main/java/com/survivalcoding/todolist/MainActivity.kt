package com.survivalcoding.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.Todo
import com.survivalcoding.todolist.adapter.TodoAdapter
import java.util.*

class MainActivity : AppCompatActivity() {

    private val items = mutableListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TodoAdapter(this, items)

        val editTitle = findViewById<EditText>(R.id.editText_title)

        findViewById<ListView>(R.id.list_view).adapter = adapter

        findViewById<Button>(R.id.button_add).setOnClickListener {
            if (editTitle.text.trim().isNotEmpty()) {
                items.add(
                    Todo(
                        editTitle.text.toString(),
                        Calendar.getInstance().time.toString()
                    )
                )
                adapter.notifyDataSetChanged()

                editTitle.text.clear()
            }
        }
    }
}
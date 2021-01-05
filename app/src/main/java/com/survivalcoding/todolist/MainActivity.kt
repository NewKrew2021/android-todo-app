package com.survivalcoding.todolist

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoList = findViewById<ListView>(R.id.todoList)
        val editTitle = findViewById<ImageView>(R.id.editTitle)
        val isDone = findViewById<RadioButton>(R.id.isDoneButton)

        var data = listOf(
                Todos(false, "Todo1"),
                Todos(false, "Todo1"),
                Todos(false, "Todo1"),
                Todos(false, "Todo1"),
                Todos(false, "Todo1"),
        )

        val todoAdapter = TodosAdapter(data)
        todoList.adapter = todoAdapter

        todoList.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "$position 선택", Toast.LENGTH_SHORT).show()
        }
        
        val addTodo = findViewById<Button>(R.id.addTodo)
        addTodo.setOnClickListener { view ->
            Toast.makeText(this, "할일 추가", Toast.LENGTH_SHORT).show()
        }
    }
}
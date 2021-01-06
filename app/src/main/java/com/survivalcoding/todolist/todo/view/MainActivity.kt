package com.survivalcoding.todolist.todo.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.todo.adapter.RecyclerViewAdapter
import com.survivalcoding.todolist.todo.adapter.Todos

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val todoListBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = todoListBinding.root

        setContentView(view)

        var data = mutableListOf<Todos>(
            Todos(false, "Todo1"),
            Todos(false, "Todo1"),
            Todos(false, "Todo1"),
            Todos(false, "Todo1"),
            Todos(false, "Todo1"),
            Todos(false, "Todo1"),
            Todos(false, "Todo1"),
            Todos(false, "Todo1"),
        )

        val todoAdapter = RecyclerViewAdapter(data)
        todoListBinding.todoList.adapter = todoAdapter

        // 좀 더 좋은 코드를 봤던것 같은데....
        todoListBinding.addTodo.setOnClickListener { view ->
            val todoText: String = todoListBinding.todoString.text.toString()
            if (todoText == "") {
                Toast.makeText(this, "값을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else {
                data.add(Todos(false, todoText))
                Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
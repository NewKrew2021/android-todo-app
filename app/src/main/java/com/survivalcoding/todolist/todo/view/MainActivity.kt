package com.survivalcoding.todolist.todo.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.todo.adapter.RecyclerViewAdapter
import com.survivalcoding.todolist.todo.model.TodoItems
import java.util.*

class MainActivity : AppCompatActivity() {

    val data = mutableListOf<TodoItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val todoListBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = todoListBinding.root
        setContentView(view)

        val todoAdapter = RecyclerViewAdapter(data)
        todoListBinding.todoList.adapter = todoAdapter

        ////


        // 좀 더 좋은 코드를 봤던것 같은데....
        todoListBinding.addTodo.setOnClickListener { view ->
            val todoText: String = todoListBinding.todoString.text.toString()
            if (todoText.trim().isEmpty()) {    // todoText == ""
                Toast.makeText(this, "값을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else {
                data.add(TodoItems(false, todoText))
                Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("listData", data as ArrayList<TodoItems>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val list = savedInstanceState.getParcelableArrayList<TodoItems>("listData")
        list?.let {
            data.clear()
            data.addAll(it.toMutableList())
//            data.notifyDataSetChanged // 메인에서 데이터를 가지고있어서 notifyDataSetChanged메소드가 없다...
        }
    }
}
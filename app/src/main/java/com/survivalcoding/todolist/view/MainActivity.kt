package com.survivalcoding.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.model.TodoItem
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var todoListAdapter: TodoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        todoListAdapter = TodoListAdapter()
        binding.apply {
            todoListView.adapter = todoListAdapter
            mainLayout.setOnClickListener {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchEdit.windowToken, 0)
            }
            addButton.setOnClickListener {
                if (searchEdit.text.toString().trim() != "") {
                    todoListAdapter.addItem(TodoItem(searchEdit.text.toString(), "d-30", false, false))
                    searchEdit.text.clear()
                }
            }
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("todoList", todoListAdapter.getItems() as ArrayList<TodoItem>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val todoList = savedInstanceState.getParcelableArrayList<TodoItem>("todoList")
        todoList?.let {
            todoListAdapter.getItems().clear()
            todoListAdapter.getItems().addAll(it.toMutableList())
            todoListAdapter.notifyDataSetChanged()
        }
    }

}
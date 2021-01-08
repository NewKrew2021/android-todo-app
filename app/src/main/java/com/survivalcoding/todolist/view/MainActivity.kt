package com.survivalcoding.todolist.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.util.getCurrentTime

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeView()
        setOnClickListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            SAVE_INSTANCE_TODO_ITEM_KEY,
            adapter.list as ArrayList<TodoItem>
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val list = savedInstanceState.getParcelableArrayList<TodoItem>(SAVE_INSTANCE_TODO_ITEM_KEY)
        list?.let {
            adapter.list.clear()
            adapter.list.addAll(it)
        }
    }

    private fun setOnClickListener() {
        binding.apply {
            registerButton.setOnClickListener {
                toDoEditText.text.apply {
                    val time = getCurrentTime()
                    adapter.addNewTodo(TodoItem(false, toString(), time))
                    clear()
                }
            }
            sortButton.setOnClickListener {
                val sortOptions = resources.getStringArray(R.array.sort_options)
                // 시간 순으로 정렬
                if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[0]) {
                    adapter.sortByTime()
                }
                // 사전 순으로 정렬
                else if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[1]) {
                    adapter.sortByTitle()
                }
            }
        }
    }

    private fun initializeView() {
        adapter = TodoListAdapter(mutableListOf())
        binding.toDoList.apply {
            this.adapter = this@MainActivity.adapter
        }
        // Spinner 생성
        ArrayAdapter.createFromResource(
            this,
            R.array.sort_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sortOptionSpinner.adapter = adapter
        }
    }

    companion object {
        const val SAVE_INSTANCE_TODO_ITEM_KEY = "todoList"
    }
}
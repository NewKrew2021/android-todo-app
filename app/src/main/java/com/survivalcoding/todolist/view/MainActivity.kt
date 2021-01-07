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

    private fun setOnClickListener() {
        binding.registerButton.setOnClickListener {
            binding.toDoEditText.text.apply {
                val time = getCurrentTime()
                adapter.addNewTodo(TodoItem(false, toString(), time))
                clear()
            }
        }
        binding.sortButton.setOnClickListener {
            val sortOptions = resources.getStringArray(R.array.sort_options)
            if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[0]) {
                adapter.sortByTime()
            } else if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[1]) {
                adapter.sortByTitle()
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
}
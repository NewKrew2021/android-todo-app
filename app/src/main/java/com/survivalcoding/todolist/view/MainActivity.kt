package com.survivalcoding.todolist.view

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
            Log.d(
                MainActivity::javaClass.name,
                "setOnClickListener: ${binding.sortOptionSpinner.selectedItem} ${sortOptions[0]} ${sortOptions[1]}"
            )
            if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[0]) {
                Log.d(MainActivity::javaClass.name, "setOnClickListener: HI")
                adapter.sortByTime()
            } else if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[1]) {
                adapter.sortByTitle()
                Log.d(MainActivity::javaClass.name, "setOnClickListener: Hello")
            }
        }
    }

    private fun initializeView() {
        adapter = TodoListAdapter(mutableListOf())
        binding.toDoList.apply {
            this.adapter = this@MainActivity.adapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
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
package com.survivalcoding.todolist.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.util.getCurrentTime
import com.survivalcoding.todolist.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoListAdapter
    private val viewModel by lazy {
        TodoViewModel()
    }

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
            viewModel.todoList as ArrayList<TodoItem>
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getParcelableArrayList<TodoItem>(SAVE_INSTANCE_TODO_ITEM_KEY)?.let {
            viewModel.clearTodoList()
            it.forEach { todo ->
                viewModel.addTodo(todo)
            }
            updateTodoList()
        }
    }

    private fun updateTodoList() {
        adapter.submitList(viewModel.todoList.toList())
    }

    private fun setOnClickListener() {
        binding.apply {
            registerButton.setOnClickListener {
                toDoEditText.text.apply {
                    val time = getCurrentTime()
                    // id 값을 ViewModel 에서 관리해주기 때문에 처음에 등록할 때는 NEW_TODO_TASK 로 지정
                    viewModel.addTodo(TodoItem(NEW_TODO_TASK, false, toString(), time))
                    clear()
                }
                updateTodoList()
            }
            sortButton.setOnClickListener {
                val sortOptions = resources.getStringArray(R.array.sort_options)
                // 시간 순으로 정렬
                if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[0]) {
                    viewModel.sortByTime()
                    updateTodoList()
                }
                // 사전 순으로 정렬
                else if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[1]) {
                    viewModel.sortByTitle()
                    updateTodoList()
                }
            }
        }
    }

    private fun initializeView() {
        adapter = TodoListAdapter {
            viewModel.removeTodo(it)
            updateTodoList()
        }
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
        const val NEW_TODO_TASK = -1
    }
}
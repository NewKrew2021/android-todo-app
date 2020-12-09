package com.survivalcoding.todolist.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.view.add.AddActivity
import com.survivalcoding.todolist.view.edit.EditActivity
import com.survivalcoding.todolist.view.main.adapter.TodoRecyclerAdapter
import com.survivalcoding.todolist.view.main.model.Todo

class MainActivity : AppCompatActivity() {
    private val viewModel = TodoViewModel()

    private lateinit var binding: ActivityMainBinding

    private val todoAdapter by lazy {
        TodoRecyclerAdapter {
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra(TODO, it)
            }
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding.memoRecyclerView) {
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = todoAdapter
        }

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ADD_REQUEST_CODE -> {
                    data?.let {
                        val todo = it.getSerializableExtra(TODO) as Todo
                        viewModel.addTodo(todo)
                        updateUi()
                    }
                }
                EDIT_REQUEST_CODE -> {
                    data?.let {
                        val todo = it.getSerializableExtra(TODO) as Todo
                        viewModel.updateTodo(todo)
                        updateUi()
                    }
                }
            }
        } else if (resultCode == RESULT_DELETE) {
            data?.let {
                val deleteTodo = it.getSerializableExtra(TODO) as Todo
                viewModel.removeTodo(deleteTodo)
                updateUi()
            }
        }
    }

    private fun updateUi() {
        todoAdapter.submitList(viewModel.items)
    }

    companion object {
        const val ADD_REQUEST_CODE = 100
        const val EDIT_REQUEST_CODE = 200
        const val RESULT_DELETE = 300
        const val TODO = "todo"
    }
}
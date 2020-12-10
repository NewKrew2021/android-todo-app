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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(STATE_LAST_ID, viewModel.id.get())
        outState.putParcelableArrayList(STATE_LIST, ArrayList(viewModel.items))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        viewModel.id.set(savedInstanceState.getInt(STATE_LAST_ID))
        savedInstanceState.getParcelableArrayList<Todo>(STATE_LIST)?.let {
            viewModel.items = it
        }
        updateUi()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ADD_REQUEST_CODE -> {
                    data?.getParcelableExtra<Todo>(TODO)?.let {
                        viewModel.addTodo(it)
                        updateUi()
                    }
                }
                EDIT_REQUEST_CODE -> {
                    data?.getParcelableExtra<Todo>(TODO)?.let {
                        viewModel.updateTodo(it)
                        updateUi()
                    }
                }
            }
        } else if (resultCode == RESULT_DELETE) {
            data?.getParcelableExtra<Todo>(TODO)?.let {
                viewModel.removeTodo(it)
                updateUi()
            }
        }
    }

    private fun updateUi() {
        todoAdapter.submitList(viewModel.getOrderedItems())
    }

    companion object {
        const val ADD_REQUEST_CODE = 100
        const val EDIT_REQUEST_CODE = 200
        const val RESULT_DELETE = 300
        const val TODO = "todo"
        const val STATE_LIST = "todoList"
        const val STATE_LAST_ID = "id"
    }
}
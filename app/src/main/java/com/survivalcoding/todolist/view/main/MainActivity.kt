package com.survivalcoding.todolist.view.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.adapter.TodoAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.extension.intentActionResult
import com.survivalcoding.todolist.extension.intentActionResultWithBundle
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.util.TODO_ITEM
import com.survivalcoding.todolist.util.TODO_ITEM_CONTENTS
import com.survivalcoding.todolist.util.TODO_ITEM_TIME
import com.survivalcoding.todolist.view.add.AddTodoActivity
import com.survivalcoding.todolist.view.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        const val ADD_TODO_REQUEST_CODE = 1000
    }

    lateinit var todoAdapter: TodoAdapter

    override fun initStartView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun afterStartView() {
        eventProcess()
        setRecyclerView()
        setSearchView()
    }

    private fun eventProcess() {
        binding.btnAddMain.setOnClickListener {
            intentActionResult(AddTodoActivity::class, ADD_TODO_REQUEST_CODE)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setRecyclerView() {
        val todoList = mutableListOf<TodoItem>()

        todoAdapter = TodoAdapter(todoList).apply {
            setModifyTodoItemListener {
                intentActionResultWithBundle(
                    AddTodoActivity::class,
                    { putParcelable(TODO_ITEM, it) },
                    ADD_TODO_REQUEST_CODE
                )
            }
        }

        binding.rvTodolistMain.apply {
            adapter = todoAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        hideKeyboard()
                        false
                    }
                    else -> false
                }
            }
        }

    }

    private fun setSearchView() {
        binding.svSearchMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                todoAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                todoAdapter.filter.filter(query)
                return true
            }
        })
    }

    private fun hideKeyboard() {
        binding.svSearchMain.clearFocus()
        val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(binding.svSearchMain.windowToken, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_TODO_REQUEST_CODE -> {
                if (data != null) {
                    todoAdapter.addTodoItem(
                        TodoItem(
                            time = data.getLongExtra(TODO_ITEM_TIME, 0),
                            contents = data.getStringExtra(TODO_ITEM_CONTENTS),
                            complete = false
                        )
                    )
                    todoAdapter.notifyDataSetChanged()
                }
            }
        }
    }


}
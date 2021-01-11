package com.survivalcoding.todolist.ui.view.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.extension.intentActionResult
import com.survivalcoding.todolist.extension.intentActionResultWithBundle
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.ui.adapter.TodoAdapter
import com.survivalcoding.todolist.ui.view.add.AddTodoActivity
import com.survivalcoding.todolist.ui.view.base.BaseActivity
import com.survivalcoding.todolist.ui.viewmodel.MainViewModel
import com.survivalcoding.todolist.util.TODO_ITEM
import com.survivalcoding.todolist.util.TODO_ITEM_CONTENTS
import com.survivalcoding.todolist.util.TODO_ITEM_TIME
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        const val ADD_TODO_REQUEST_CODE = 1000
    }

    override val viewModel: MainViewModel by viewModel()

    lateinit var todoAdapter: TodoAdapter
    lateinit var searchView: SearchView

    override fun initStartView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun afterStartView() {
        eventProcess()
        setRecyclerView()
        setToolbar()
    }

    private fun eventProcess() {
        binding.btnAddMain.setOnClickListener {
            intentActionResult(AddTodoActivity::class, ADD_TODO_REQUEST_CODE)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setRecyclerView() {
        todoAdapter = TodoAdapter().apply {
            setTodoList(viewModel.todoList)
            setModifyTodoItemListener {
                intentActionResultWithBundle(
                    AddTodoActivity::class,
                    { putParcelable(TODO_ITEM, it) },
                    ADD_TODO_REQUEST_CODE
                )
            }
            setSortTodoItemListener {
                viewModel.sortTodoItem()
                todoAdapter.setTodoList(viewModel.todoList)
            }
        }

        binding.rvTodolistMain.apply {
            adapter = todoAdapter
            setHasFixedSize(true)
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
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    private fun setToolbar() {
        setSupportActionBar(binding.toolbarMain)
    }

    private fun hideKeyboard() {
        searchView.clearFocus()
        val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(searchView.windowToken, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_TODO_REQUEST_CODE -> {
                if (data != null) {
                    viewModel.addTodoItem(
                        TodoItem(
                            time = data.getLongExtra(TODO_ITEM_TIME, 0),
                            contents = data.getStringExtra(TODO_ITEM_CONTENTS),
                            complete = false
                        )
                    )
                    todoAdapter.setTodoList(viewModel.todoList)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        setSearchView()
        return true
    }


}
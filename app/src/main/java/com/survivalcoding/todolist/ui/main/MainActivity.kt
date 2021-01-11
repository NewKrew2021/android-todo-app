package com.survivalcoding.todolist.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.ui.add.AddActivity
import com.survivalcoding.todolist.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TodoListAdapter
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.search.setOnClickListener {
            updateUi(viewModel.getFilteredItems(binding.input.text.toString()))
        }

        adapter = TodoListAdapter(
            sort = { viewModel.sortItems() },
            remove = { targetItem -> viewModel.removeItem(targetItem) },
            update = { updateUi() }
        )

        binding.list.apply {
            adapter = this@MainActivity.adapter
            addItemDecoration(VerticalSpaceItemDecoration(ITEM_VERTICAL_INTERVAL))
            itemAnimator = DefaultItemAnimator().apply {
                changeDuration = 100
                moveDuration = 100
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_REQUEST_CODE) {
            data?.let { resultIntent ->
                resultIntent.extras?.getParcelable<TodoItem>(AddActivity.NEW_TODO)?.let {
                    viewModel.addItem(it)
                    updateUi()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                startActivityForResult(Intent(this, AddActivity::class.java), ADD_REQUEST_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList(SAVED_LIST_KEY, viewModel.list as ArrayList<TodoItem>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getParcelableArrayList<TodoItem>(SAVED_LIST_KEY)?.let {
            viewModel.resetItems(it)
            updateUi()
        }
    }

    private fun updateUi() {
        adapter.submitList(viewModel.list.toList())
    }

    private fun updateUi(list: List<TodoItem>) {
        adapter.submitList(list)
    }

    companion object {
        private const val ITEM_VERTICAL_INTERVAL = 12
        private const val SAVED_LIST_KEY = "SAVED_LIST_KEY"
        private const val ADD_REQUEST_CODE = 100
    }

}
package com.survivalcoding.todolist.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.model.TodoItem
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

        binding.add.setOnClickListener {
            addItem()
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

    private fun addItem() {

        if (binding.input.text.toString().isNotEmpty()) {
            viewModel.addItem(binding.input.text.toString())
                .also {
                    binding.input.text = null
                    binding.list.layoutManager?.scrollToPosition(0)
                }
            updateUi()
        } else {
            Toast.makeText(this, NO_CONTENT_MESSAGE, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ITEM_VERTICAL_INTERVAL = 12
        private const val NO_CONTENT_MESSAGE = "내용을 입력해주세요."
        private const val SAVED_LIST_KEY = "SAVED_LIST_KEY"
    }

}
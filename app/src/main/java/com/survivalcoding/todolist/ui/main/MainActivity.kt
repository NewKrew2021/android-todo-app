package com.survivalcoding.todolist.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.survivalcoding.todolist.adapter.TodoItem
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TodoListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            addItem()
        }

        adapter = TodoListAdapter()

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

        outState.putParcelableArrayList(SAVED_LIST_KEY, adapter.list as ArrayList<TodoItem>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getParcelableArrayList<TodoItem>(SAVED_LIST_KEY)?.let {
            adapter.resetItems(it)
        }
    }

    private fun addItem() {

        if (binding.input.text.toString().isNotEmpty()) {
            adapter.addItem(
                TodoItem(
                    binding.input.text.toString(),
                    false,
                    Calendar.getInstance().timeInMillis
                )
            ).also {
                binding.input.text = null
                binding.list.layoutManager?.scrollToPosition(0)
            }
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
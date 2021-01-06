package com.survivalcoding.todolist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.model.Todo
import com.survivalcoding.todolist.util.DateUtils
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val items = mutableListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TodoAdapter(this, items)

        binding.apply {
            recyclerView.adapter = adapter

            buttonAdd.setOnClickListener {
                if (editTextTitle.text.trim().isNotEmpty()) {
                    items.add(
                        index = 0,
                        element = Todo(
                            editTextTitle.text.toString(),
                            DateUtils.dateToString(Calendar.getInstance().time)
                        )
                    )
                    adapter.notifyItemInserted(0)

                    recyclerView.smoothScrollToPosition(0)
                    editTextTitle.text.clear()
                }
            }
        }
    }
}
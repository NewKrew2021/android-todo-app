package com.survivalcoding.todolist.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.model.Todo
import com.survivalcoding.todolist.util.dateToString
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val items = mutableListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TodoAdapter(items, ::showToastMessage)

        binding.apply {
            recyclerView.adapter = adapter

            buttonAdd.setOnClickListener {
                if (editTextTitle.text.trim().isNotEmpty()) {
                    items.add(
                        index = 0,
                        element = Todo(
                            editTextTitle.text.toString(),
                            dateToString(Calendar.getInstance().time)
                        )
                    )
                    adapter.notifyItemInserted(0)

                    recyclerView.smoothScrollToPosition(0)
                    editTextTitle.text.clear()
                }
            }
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, "$message 삭제되었습니다.", Toast.LENGTH_SHORT).show()
    }
}
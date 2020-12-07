package com.survivalcoding.todolist.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.view.add.AddActivity
import com.survivalcoding.todolist.view.edit.EditActivity
import com.survivalcoding.todolist.view.main.adapter.TodoRecyclerAdapter
import com.survivalcoding.todolist.view.main.model.Todo

var global_id = 0

class MainActivity : AppCompatActivity() {
    private val items = mutableListOf<Todo>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = TodoRecyclerAdapter {
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra(TODO, it)
            }
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }.apply {
            submitList(items)
        }

        binding.memoRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = adapter
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
                        items.add(todo)
                        binding.memoRecyclerView.adapter?.notifyDataSetChanged()
                    }
                }
                EDIT_REQUEST_CODE -> {
                    data?.let {
                        val todo = it.getSerializableExtra(TODO) as Todo
                        val changeData = items.map { e ->
                            if (e.id == todo.id) {
                                todo
                            } else {
                                e
                            }
                        }

                        items.apply {
                            clear()
                            addAll(changeData)
                        }
                        binding.memoRecyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    companion object {
        const val ADD_REQUEST_CODE = 100
        const val EDIT_REQUEST_CODE = 200
        const val TODO = "todo"
    }
}
package com.survivalcoding.todolist.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.extension.navigateForResult
import com.survivalcoding.todolist.model.Todo
import com.survivalcoding.todolist.util.dateToString
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: TodoAdapter

    private val items = mutableListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TodoAdapter(
            items,
            showToastMessage = { message: String -> showToastMessage(message) },
            editClickEvent = { args: Bundle -> navigateForResult(EditActivity::class, args, EDIT_ACTIVITY_REQ_CODE) },
        )

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                EDIT_ACTIVITY_REQ_CODE -> {
                    data?.extras?.let {
                        if (it[TODO_ITEM_KEY] != null && it[TODO_ITEM_TITLE_KEY] != null) {
                            adapter.edit(
                                item = it[TODO_ITEM_KEY] as Todo,
                                title = it[TODO_ITEM_TITLE_KEY].toString(),
                                times = dateToString(Calendar.getInstance().time),
                            )
                        }
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(TODO_ITEM_STATE_KEY, items as ArrayList<out Todo>)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getParcelableArrayList<Todo>(TODO_ITEM_STATE_KEY)?.let {
            items.clear()
            items.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, "$message 삭제되었습니다.", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TODO_ITEM_STATE_KEY = "TODO_ITEM_STATE_KEY"
        const val TODO_ITEM_TITLE_KEY = "TODO_ITEM_TITLE_KEY"
        const val TODO_ITEM_KEY = "TODO_ITEM_KEY"

        const val EDIT_ACTIVITY_REQ_CODE = 100
    }
}
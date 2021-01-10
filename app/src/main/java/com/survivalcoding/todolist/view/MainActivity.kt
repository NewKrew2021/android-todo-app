package com.survivalcoding.todolist.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.extension.gotoActivityForResult
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.viewmodel.TodoViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var todoListAdapter: TodoListAdapter
    val todoViewModel: TodoViewModel = TodoViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        todoListAdapter = TodoListAdapter()
        binding.apply {
            todoListView.adapter = todoListAdapter
            mainLayout.setOnClickListener {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchEdit.windowToken, 0)
            }
            addButton.setOnClickListener {
                val bundle: Bundle = Bundle()
                gotoActivityForResult(MakeTodoActivity::class.java, bundle, ADD_REQUEST_CODE)
            }
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("todoList", todoViewModel.items as ArrayList<TodoItem>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val todoList = savedInstanceState.getParcelableArrayList<TodoItem>("todoList")
        todoList?.let {
            todoViewModel.items = it
        }
        updateList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.getParcelableExtra<TodoItem>("TodoItem")?.let {
                        it.id = dataId.id
                        dataId.id += 1
                        todoViewModel.add(it)
                        updateList()
                    }
                }
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ADD_REQUEST_CODE = 100
        const val EDIT_REQUEST_CODE = 200

    }

    private fun updateList() {
        todoListAdapter.submitList(todoViewModel.getItemList().toList())
    }
}

object dataId {
    var id: Int = 0
}
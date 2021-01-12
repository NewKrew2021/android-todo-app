package com.survivalcoding.todolist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity() {
    lateinit var todoListAdapter: TodoListAdapter
    lateinit var binding: ActivityMainBinding
    val todoViewModel: TodoViewModel = TodoViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragmentContainerView, MainFragment())
            }
        }

    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelableArrayList("todoList", todoViewModel.items as ArrayList<TodoItem>)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        val todoList = savedInstanceState.getParcelableArrayList<TodoItem>("todoList")
//        todoList?.let {
//            todoViewModel.items = it
//        }
//        updateList()
//    }
}

package com.survivalcoding.todolist.view

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.adapter.TodoAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.extension.intentActionWithBundle
import com.survivalcoding.todolist.model.TodoItem

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initStartView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun afterStartView() {
        eventProcess()
        setRecyclerView()
    }

    private fun eventProcess() {
        binding.btnAddMain.setOnClickListener {
            intentActionWithBundle(AddTodoActivity::class)
        }
    }

    private fun setRecyclerView() {
        val todoList = mutableListOf(
            TodoItem("2020년 1월5일 17시47분", "첫 번째 할일", false),
            TodoItem("2020년 1월6일 13시32분", "두 번째 할일", false),
            TodoItem("2020년 1월7일 14시23분", "세 번째 할일", false),
        )

        val todoAdapter = TodoAdapter(todoList)

        binding.rvTodolistMain.apply {
            adapter = todoAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }


}
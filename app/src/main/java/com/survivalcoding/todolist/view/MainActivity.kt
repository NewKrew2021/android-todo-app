package com.survivalcoding.todolist.view

import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.Util.IntentUtil
import com.survivalcoding.todolist.adapter.TodoAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.extension.intentActionResult
import com.survivalcoding.todolist.model.TodoItem

class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var todoAdapter : TodoAdapter

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
            intentActionResult(AddTodoActivity::class, IntentUtil.ADD_TODO_REQUEST_CODE)
        }
    }

    private fun setRecyclerView() {
        val todoList = mutableListOf<TodoItem>()

        todoAdapter = TodoAdapter(todoList)

        binding.rvTodolistMain.apply {
            adapter = todoAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            IntentUtil.ADD_TODO_REQUEST_CODE -> {
                if (data != null) {
                    todoAdapter.addTodoItem(
                        TodoItem(
                            contents = data.getStringExtra("contents"),
                            time = data.getStringExtra("time"),
                            complete = false
                        )
                    )
                    todoAdapter.notifyDataSetChanged()
                }
            }

        }
    }


}
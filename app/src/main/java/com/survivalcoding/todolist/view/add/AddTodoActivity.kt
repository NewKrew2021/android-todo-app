package com.survivalcoding.todolist.view.add

import android.app.Activity
import android.content.Intent
import android.text.Editable
import com.survivalcoding.todolist.databinding.ActivityAddTodoBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.view.base.BaseActivity
import java.util.*

class AddTodoActivity : BaseActivity<ActivityAddTodoBinding>() {

    override fun initStartView() {
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun afterStartView() {
        eventProcess()
        isModifyTodoItem()
    }

    private fun isModifyTodoItem() {
        if (intent != null) {
            val todoItem = intent.getParcelableExtra<TodoItem>("todoItem")
            if (todoItem != null) {
                binding.edtContentsAddTodo.text =
                    Editable.Factory.getInstance().newEditable(todoItem.contents)
            }
        }
    }

    private fun eventProcess() {
        binding.btnSaveAddTodo.setOnClickListener {
            val saveIntent = Intent().apply {
                putExtra("contents", binding.edtContentsAddTodo.text.toString())
                putExtra("time", Calendar.getInstance().timeInMillis)
            }
            setResult(Activity.RESULT_OK, saveIntent)
            finish()
        }

        binding.btnCancelAddTodo.setOnClickListener {
            val todoItem = intent.getParcelableExtra<TodoItem>("todoItem")
            val cancelIntent = Intent().apply {
                if (todoItem != null) {
                    putExtra("contents", todoItem.contents)
                    putExtra("time", Calendar.getInstance().timeInMillis)
                }
            }
            setResult(Activity.RESULT_OK, cancelIntent)
            finish()
        }
    }
}
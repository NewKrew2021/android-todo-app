package com.survivalcoding.todolist.ui.view.add

import android.app.Activity
import android.content.Intent
import android.text.Editable
import com.survivalcoding.todolist.databinding.ActivityAddTodoBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.ui.view.base.BaseActivity
import com.survivalcoding.todolist.util.TODO_ITEM
import com.survivalcoding.todolist.util.TODO_ITEM_CONTENTS
import com.survivalcoding.todolist.util.TODO_ITEM_TIME
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
            val todoItem = intent.getParcelableExtra<TodoItem>(TODO_ITEM)
            if (todoItem != null) {
                binding.edtContentsAddTodo.text =
                    Editable.Factory.getInstance().newEditable(todoItem.contents)
            }
        }
    }

    private fun eventProcess() {
        binding.btnSaveAddTodo.setOnClickListener {
            val saveIntent = Intent().apply {
                putExtra(TODO_ITEM_CONTENTS, binding.edtContentsAddTodo.text.toString())
                putExtra(TODO_ITEM_TIME, Calendar.getInstance().timeInMillis)
            }
            setResult(Activity.RESULT_OK, saveIntent)
            finish()
        }

        binding.btnCancelAddTodo.setOnClickListener {
            val todoItem = intent.getParcelableExtra<TodoItem>(TODO_ITEM)
            val cancelIntent = Intent().apply {
                if (todoItem != null) {
                    putExtra(TODO_ITEM_CONTENTS, todoItem.contents)
                    putExtra(TODO_ITEM_TIME, todoItem.time)
                }
            }
            setResult(Activity.RESULT_OK, cancelIntent)
            finish()
        }
    }
}
package com.survivalcoding.todolist.todo.view.edit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.databinding.ActivityAddBinding
import com.survivalcoding.todolist.todo.view.main.MainActivity
import com.survivalcoding.todolist.todo.view.model.Todo
import java.text.SimpleDateFormat

class EditActivity : AppCompatActivity() {
    private lateinit var addBinding: ActivityAddBinding
    private lateinit var todo: Todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        intent?.getParcelableExtra<Todo>(MainActivity.INTENT_KEY)?.let {
            todo = it
            addBinding.todoEditText.setText(todo.text)
        }

        addBinding.dueDate.date = todo.dueDate
        var dueDate = todo.dueDate
        addBinding.addTodo.text = "수정"

        val format = SimpleDateFormat(MainActivity.DATE_FORMAT)

        addBinding.cancelButton.setOnClickListener { finish() }

        addBinding.dueDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val dateString = "$year-${month + 1}-$dayOfMonth"
            dueDate = format.parse(dateString).time
        }

        addBinding.addTodo.setOnClickListener {
            if (addBinding.todoEditText.text.toString() == todo.text) {
                todo.text = addBinding.todoEditText.text.toString()
                todo.dueDate = dueDate

                val intent = Intent().apply {
                    putExtra(MainActivity.INTENT_KEY, todo)
                }
                setResult(RESULT_OK, intent)
                finish()
            } else {
                finish()
            }
        }
    }
}
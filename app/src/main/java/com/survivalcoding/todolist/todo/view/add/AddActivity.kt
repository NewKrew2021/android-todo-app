package com.survivalcoding.todolist.todo.view.add

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.databinding.ActivityAddBinding
import com.survivalcoding.todolist.todo.view.main.MainActivity
import com.survivalcoding.todolist.todo.view.model.Todo
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    private lateinit var addBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        var dueDate = Date().time
        val format = SimpleDateFormat(MainActivity.DATE_FORMAT)

        addBinding.cancelButton.setOnClickListener { finish() }
        addBinding.dueDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val dateString = "$year-${month + 1}-$dayOfMonth"
            dueDate = format.parse(dateString).time
        }

        addBinding.addTodo.setOnClickListener {
            val todoString = addBinding.todoEditText.text.toString()
            if (todoString.trim().isEmpty()) {
                Toast.makeText(this, "할 일을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else {
                val item = Todo(false, todoString, dueDate)
                val intent = Intent().putExtra(MainActivity.INTENT_KEY, item)

                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}
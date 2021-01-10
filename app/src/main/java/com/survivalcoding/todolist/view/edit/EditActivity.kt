package com.survivalcoding.todolist.view.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.databinding.ActivityEditBinding
import com.survivalcoding.todolist.util.dateToString
import com.survivalcoding.todolist.view.main.MainActivity
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.*

class EditActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val todo: Todo? = intent.getParcelableExtra<Todo>(MainActivity.TODO_KEY)

        with(binding) {
            buttonOk.setOnClickListener {
                if (editTextTitle.text.trim().isNotEmpty()) {
                    if (todo == null) {
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                    else {
                        todo.title = editTextTitle.text.toString()
                        todo.times = dateToString(Calendar.getInstance().time)

                        val data = Intent().apply {
                            putExtra(MainActivity.TODO_TITLE_KEY, editTextTitle.text)
                            putExtra(MainActivity.TODO_KEY, todo)
                        }
                        setResult(Activity.RESULT_OK, data)
                        finish()
                    }
                }
            }

            buttonCancel.setOnClickListener {
                finish()
            }
        }
    }
}
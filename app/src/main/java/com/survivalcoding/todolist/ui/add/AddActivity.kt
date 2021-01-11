package com.survivalcoding.todolist.ui.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.databinding.ActivityAddBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.ui.main.MainActivity
import java.util.*

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {

            if (binding.title.text.toString().isNotEmpty()) {
                returnToMain()
            } else {
                Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun returnToMain() {

        val title = binding.title.text.toString()
        val timeStamp = Calendar.getInstance().timeInMillis

        val newTodoItem = Bundle().apply {
            putParcelable(NEW_TODO, TodoItem(title, false, timeStamp))
        }
        val result = Intent(this, MainActivity::class.java).apply {
            putExtras(newTodoItem)
        }

        setResult(Activity.RESULT_OK, result)
        finish()
    }

    companion object {
        const val NEW_TODO = "new todo"
    }
}
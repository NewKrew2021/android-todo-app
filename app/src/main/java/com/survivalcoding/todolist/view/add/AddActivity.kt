package com.survivalcoding.todolist.view.add

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.databinding.ActivityAddBinding
import com.survivalcoding.todolist.view.main.MainActivity
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.*

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.addButton.setOnClickListener {
            if (binding.todoText.text.isNotEmpty()) {
                val intent = Intent().apply {
                    val todo = binding.todoText.text.toString()
                    putExtra(MainActivity.TODO, Todo(todo, Date().time))
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}
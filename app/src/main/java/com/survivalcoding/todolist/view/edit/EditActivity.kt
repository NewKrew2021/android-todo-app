package com.survivalcoding.todolist.view.edit

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityAddBinding
import com.survivalcoding.todolist.view.main.MainActivity
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.*

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding

    private lateinit var todo: Todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.addButton.text = "수정"

        intent?.let {
            todo = it.getSerializableExtra(MainActivity.TODO) as Todo
            binding.todoText.setText(todo.todo)
        }

        binding.addButton.setOnClickListener {
            if (todo.todo != binding.todoText.text.toString()) {
                todo.todo = binding.todoText.text.toString()
                todo.datetime = Date().time

                val intent = Intent().apply {
                    putExtra(MainActivity.TODO, todo)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_delete -> {
                val intent = Intent().apply {
                    putExtra(MainActivity.TODO, todo)
                }
                setResult(MainActivity.RESULT_DELETE, intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
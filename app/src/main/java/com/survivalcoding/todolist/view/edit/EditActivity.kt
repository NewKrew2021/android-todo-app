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

        val item: Todo? = intent.getParcelableExtra<Todo>(MainActivity.TODO_ITEM_KEY)

        binding.apply {
            buttonOk.setOnClickListener {
                if (editTextTitle.text.trim().isNotEmpty()) {
                    if (item == null) {
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                    else {
                        item.title = editTextTitle.text.toString()
                        item.times = dateToString(Calendar.getInstance().time)

                        val data = Intent().apply {
                            putExtra(MainActivity.TODO_ITEM_TITLE_KEY, editTextTitle.text)
                            putExtra(MainActivity.TODO_ITEM_KEY, item)
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
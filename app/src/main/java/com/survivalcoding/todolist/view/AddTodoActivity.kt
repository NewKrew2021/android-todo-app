package com.survivalcoding.todolist.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.survivalcoding.todolist.databinding.ActivityAddTodoBinding
import java.time.LocalDate

class AddTodoActivity : BaseActivity<ActivityAddTodoBinding>() {

    override fun initStartView() {
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun afterStartView() {
        eventProcess()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun eventProcess() {
        binding.btnSaveAddTodo.setOnClickListener {
            val intent = Intent().apply {
                putExtra("contents", binding.edtContentsAddTodo.text.toString())
                putExtra("time", LocalDate.now().toString())
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.btnCancelAddTodo.setOnClickListener {
            onBackPressed()
        }
    }

}
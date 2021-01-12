package com.survivalcoding.todolist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.databinding.ActivityMakeTodoBinding

class MakeTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        val binding = ActivityMakeTodoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}
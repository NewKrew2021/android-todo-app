package com.survivalcoding.todolist.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.App
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.extension.addTransaction
import com.survivalcoding.todolist.factory.TodoFragmentFactory
import com.survivalcoding.todolist.view.main.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory =
            TodoFragmentFactory((application as App).repository)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            addTransaction<MainFragment>(R.id.fragment_container_view)
        }
    }

    companion object {
        const val TODO_KEY = "TODO_KEY"
    }
}
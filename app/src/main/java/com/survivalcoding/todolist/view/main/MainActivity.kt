package com.survivalcoding.todolist.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.survivalcoding.todolist.App
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.factory.TodoFragmentFactory
import com.survivalcoding.todolist.view.main.fragment.MainFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val todoRepository by lazy { (application as App).todoRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = TodoFragmentFactory(todoRepository)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MainFragment>(R.id.fragment_container_view, "main")
            }
        }
    }

}
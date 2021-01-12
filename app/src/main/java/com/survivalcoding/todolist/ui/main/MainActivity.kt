package com.survivalcoding.todolist.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.TodoApp
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.factory.MainFragmentFactory
import com.survivalcoding.todolist.utils.NavigationUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val todoRepository by lazy { (application as TodoApp).todoRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.fragmentFactory = MainFragmentFactory(todoRepository)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            NavigationUtil.openMainFragment(supportFragmentManager)
        }
    }
}
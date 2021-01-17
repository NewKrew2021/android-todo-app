package com.survivalcoding.todolist.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.TodoApp
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.factory.MainFragmentFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val todoRepository by lazy { (application as TodoApp).todoRepository }

    override fun onCreate(savedInstanceState: Bundle?) {

        supportFragmentManager.fragmentFactory = MainFragmentFactory(todoRepository)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<MainFragment>(CONTAINER_VIEW_ID)
                setReorderingAllowed(true)
            }
        }
    }

    companion object {
        const val CONTAINER_VIEW_ID = R.id.fragment_container
    }

}
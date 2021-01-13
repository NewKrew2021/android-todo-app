package com.survivalcoding.todolist.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.TodoViewModel
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.factory.TodoFragmentFactory
import com.survivalcoding.todolist.view.main.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private val viewModel = TodoViewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = TodoFragmentFactory(viewModel)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, MainFragment::class.java, null)
            }
        }
    }

    companion object {
        const val TODO_ID_KEY = "TODO_ID_KEY"
        const val TODO_STATE_KEY = "TODO_STATE_KEY"
        const val TODO_KEY = "TODO_KEY"
    }
}
package com.survivalcoding.todolist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.survivalcoding.todolist.App
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.factory.TodoFragmentFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

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
                add(R.id.fragmentContainerView, MainFragment(todoRepository))
            }
        }

    }

}

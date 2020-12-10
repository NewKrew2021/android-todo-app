package com.survivalcoding.todolist.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.survivalcoding.todolist.App
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.factory.MyFragmentFactory
import com.survivalcoding.todolist.view.add.AddFragment
import com.survivalcoding.todolist.view.edit.EditFragment

class MainActivity : AppCompatActivity(),
    AddFragment.OnTodoAddListener,
    EditFragment.OnTodoEditListener {
    private lateinit var binding: ActivityMainBinding

    private val todoRepository by lazy { (application as App).todoRepository }

    override fun onCreate(savedInstanceState: Bundle?) {

        supportFragmentManager.fragmentFactory = MyFragmentFactory(todoRepository)
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

    companion object {
        const val TODO = "todo"
        const val STATE_LIST = "todoList"
        const val STATE_LAST_ID = "id"
    }

    override fun onTodoAdded() {
        updateUi()
    }

    private fun updateUi() {
        supportFragmentManager.commit {
            supportFragmentManager.findFragmentByTag("main")?.let { mainFragment ->
                detach(mainFragment)
                attach(mainFragment)
            }
        }
    }

    override fun onTodoEdited() {
        updateUi()
    }
}
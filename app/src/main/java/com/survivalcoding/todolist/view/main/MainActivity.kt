package com.survivalcoding.todolist.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.TodoViewModel
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.factory.TodoFragmentFactory
import com.survivalcoding.todolist.view.main.fragment.MainFragment
import com.survivalcoding.todolist.view.main.model.Todo

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(TODO_ID_KEY, viewModel.id.get())
        outState.putParcelableArrayList(TODO_STATE_KEY, viewModel.items as ArrayList<out Todo>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        viewModel.id.set(savedInstanceState.getInt(TODO_ID_KEY))
        savedInstanceState.getParcelableArrayList<Todo>(TODO_STATE_KEY)?.let {
            viewModel.addAll(it)
        }
    }

    companion object {
        const val TODO_ID_KEY = "TODO_ID_KEY"
        const val TODO_STATE_KEY = "TODO_STATE_KEY"
        const val TODO_TITLE_KEY = "TODO_TITLE_KEY"
        const val TODO_KEY = "TODO_KEY"
    }
}
package com.survivalcoding.todolist.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.model.TodoRepository

class TodoFragmentFactory(private val repository: TodoRepository) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            TodoFragment::class.java -> TodoFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
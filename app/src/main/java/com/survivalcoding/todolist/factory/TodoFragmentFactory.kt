package com.survivalcoding.todolist.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.data.TodoViewModel
import com.survivalcoding.todolist.view.main.fragment.EditFragment
import com.survivalcoding.todolist.view.main.fragment.MainFragment

class TodoFragmentFactory(private val repository: DefaultTodoRepository): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(loadFragmentClass(classLoader, className)) {
            MainFragment::class.java -> MainFragment(repository)
            EditFragment::class.java -> EditFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
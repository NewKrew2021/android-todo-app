package com.survivalcoding.todolist.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.data.TodoDefaultRepository
import com.survivalcoding.todolist.view.main.MainFragment

class TodoFragmentFactory(private val repository: TodoDefaultRepository) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            MainFragment::class.java -> MainFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
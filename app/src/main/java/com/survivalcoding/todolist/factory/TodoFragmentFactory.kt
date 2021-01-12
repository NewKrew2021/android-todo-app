package com.survivalcoding.todolist.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.data.TodoViewModel
import com.survivalcoding.todolist.view.main.fragment.MainFragment

class TodoFragmentFactory(private val viewModel: TodoViewModel): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(loadFragmentClass(classLoader, className)) {
            MainFragment::class.java -> MainFragment(viewModel)
            else -> super.instantiate(classLoader, className)
        }
    }
}
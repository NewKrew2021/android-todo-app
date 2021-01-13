package com.survivalcoding.todolist.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.repository.DefaultTodoRepository
import com.survivalcoding.todolist.view.MainFragment
import com.survivalcoding.todolist.view.MakeTodoFragment

class TodoFragmentFactory(val repository: DefaultTodoRepository) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(loadFragmentClass(classLoader, className)){
            MakeTodoFragment::class.java -> MakeTodoFragment(repository)
            MainFragment::class.java -> MainFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
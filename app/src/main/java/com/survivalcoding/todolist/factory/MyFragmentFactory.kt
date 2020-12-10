package com.survivalcoding.todolist.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.data.TodoRepository
import com.survivalcoding.todolist.view.add.AddFragment
import com.survivalcoding.todolist.view.edit.EditFragment
import com.survivalcoding.todolist.view.main.MainFragment

class MyFragmentFactory(private val repository: TodoRepository) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            MainFragment::class.java -> MainFragment(repository)
            AddFragment::class.java -> AddFragment(repository)
            EditFragment::class.java -> EditFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
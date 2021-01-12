package com.survivalcoding.todolist.todo.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.todo.data.TodoData
import com.survivalcoding.todolist.todo.view.add.AddFragment
import com.survivalcoding.todolist.todo.view.edit.EditFragment
import com.survivalcoding.todolist.todo.view.main.MainFragment

class MainFragmentFactory(private val data: TodoData) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            MainFragment::class.java -> MainFragment(data)
            AddFragment::class.java -> AddFragment(data)
            EditFragment::class.java -> EditFragment(data)
            else -> super.instantiate(classLoader, className)
        }
    }
}
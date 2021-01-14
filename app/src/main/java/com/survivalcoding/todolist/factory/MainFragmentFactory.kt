package com.survivalcoding.todolist.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.data.database.TodoSqliteRepository
import com.survivalcoding.todolist.ui.edit.EditFragment
import com.survivalcoding.todolist.ui.main.MainFragment
import com.survivalcoding.todolist.ui.todo.TodoFragment

class MainFragmentFactory(private val repository: TodoSqliteRepository) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            MainFragment::class.java -> MainFragment(repository)
            EditFragment::class.java -> EditFragment(repository)
            TodoFragment::class.java -> TodoFragment(repository)
            else -> super.instantiate(classLoader, className)
        }
    }
}
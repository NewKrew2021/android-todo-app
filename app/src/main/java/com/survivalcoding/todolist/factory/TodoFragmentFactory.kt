package com.survivalcoding.todolist.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.view.MakeTodoFragment

class TodoFragmentFactory(val mode: String, val todoItem: TodoItem) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(loadFragmentClass(classLoader, className)){
            MakeTodoFragment::class.java -> MakeTodoFragment(mode, todoItem)
            else -> super.instantiate(classLoader, className)
        }
    }
}
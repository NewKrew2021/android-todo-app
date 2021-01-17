package com.survivalcoding.todolist.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.view.FirstFragment
import com.survivalcoding.todolist.view.ListFragment

class MyFragmentFactory(private val repository: DefaultTodoRepository) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            FirstFragment::class.java -> FirstFragment("hello")
            ListFragment::class.java -> ListFragment(repository)

            else -> super.instantiate(classLoader, className)
        }
    }
}
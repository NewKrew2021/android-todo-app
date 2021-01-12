package com.survivalcoding.todolist.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.survivalcoding.todolist.view.FirstFragment

class MyFragmentFactory(private val data: String) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (loadFragmentClass(classLoader, className)) {
            FirstFragment::class.java -> FirstFragment(data)

            else -> super.instantiate(classLoader, className)
        }
    }
}
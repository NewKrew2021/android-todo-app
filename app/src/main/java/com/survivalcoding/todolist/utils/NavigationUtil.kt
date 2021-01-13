package com.survivalcoding.todolist.utils

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.ui.edit.EditFragment
import com.survivalcoding.todolist.ui.main.MainFragment
import com.survivalcoding.todolist.ui.todo.TodoFragment

object NavigationUtil {

    private const val CONTAINER_VIEW_ID = R.id.fragment_container
    private const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT"
    private const val ADD_FRAGMENT_TAG = "ADD_FRAGMENT"
    private const val EDIT_FRAGMENT_TAG = "EDIT_FRAGMENT"

    fun openMainFragment(fragmentManager: FragmentManager) {

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        fragmentManager.commit {
            replace<MainFragment>(CONTAINER_VIEW_ID, MAIN_FRAGMENT_TAG)
            setReorderingAllowed(true)
        }
    }

    fun openTodoFragment(fragmentManager: FragmentManager, todoItem: TodoItem) {

        val bundle = bundleOf(Pair("picked", todoItem))

        fragmentManager.commit {
            replace<TodoFragment>(CONTAINER_VIEW_ID, ADD_FRAGMENT_TAG, args = bundle)
            addToBackStack(ADD_FRAGMENT_TAG)
            setReorderingAllowed(true)
        }
    }

    fun openEditFragment(fragmentManager: FragmentManager, todoItem: TodoItem? = null) {

        val bundle = bundleOf(Pair("picked", todoItem))

        fragmentManager.commit {
            replace<EditFragment>(CONTAINER_VIEW_ID, EDIT_FRAGMENT_TAG, args = bundle)
            addToBackStack(EDIT_FRAGMENT_TAG)
            setReorderingAllowed(true)
        }
    }

    fun popThisFragment(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack()
    }
}
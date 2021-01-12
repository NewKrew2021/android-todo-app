package com.survivalcoding.todolist.utils

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.ui.add.AddFragment
import com.survivalcoding.todolist.ui.main.MainFragment

object NavigationUtil {

    private const val CONTAINER_VIEW_ID = R.id.fragment_container
    private const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT"
    private const val ADD_FRAGMENT_TAG = "ADD_FRAGMENT"

    fun openMainFragment(fragmentManager: FragmentManager) {

        fragmentManager.commit {
            replace<MainFragment>(CONTAINER_VIEW_ID, MAIN_FRAGMENT_TAG)
            setReorderingAllowed(true)
        }
    }

    fun openAddFragment(fragmentManager: FragmentManager) {

        fragmentManager.commit {
            replace<AddFragment>(CONTAINER_VIEW_ID, ADD_FRAGMENT_TAG)
            addToBackStack(ADD_FRAGMENT_TAG)
            setReorderingAllowed(true)
        }
    }

    fun popThisFragment(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack()
    }

}
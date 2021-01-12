package com.survivalcoding.todolist.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.survivalcoding.todolist.R

inline fun <reified F : Fragment> Fragment.replaceTransactionWithAnimation(containerViewId: Int) {
    parentFragmentManager.commit {
        setReorderingAllowed(true)
        addToBackStack(null)
        setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_right
        )
        replace<F>(containerViewId)
    }
}
package com.survivalcoding.todolist.extension

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

inline fun <reified F : Fragment> Fragment.replaceTransaction(fragmentContainerViewId: Int, bundle: Bundle? = null) {
    parentFragmentManager.commit {
        setReorderingAllowed(true)
        addToBackStack(null)
        replace(fragmentContainerViewId, F::class.java, bundle)
    }
}

fun Fragment.finish() = parentFragmentManager.popBackStack()
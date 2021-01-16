package com.survivalcoding.todolist.extension

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.ui.edit.EditFragment
import com.survivalcoding.todolist.ui.main.MainActivity.Companion.CONTAINER_VIEW_ID
import com.survivalcoding.todolist.ui.main.MainFragment

fun Fragment.openMainFragment() {
    parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    parentFragmentManager.commit {
        replace<MainFragment>(CONTAINER_VIEW_ID)
        setReorderingAllowed(true)
    }
}

fun Fragment.openEditFragmentToAdd() {
    parentFragmentManager.commit {
        replace<EditFragment>(CONTAINER_VIEW_ID)
        addToBackStack(null)
        setReorderingAllowed(true)
    }
}

inline fun <reified F : Fragment> Fragment.openFragmentWith(todoItem: TodoItem) {

    val bundle = bundleOf(Pair("picked", todoItem))

    parentFragmentManager.commit {
        replace<F>(CONTAINER_VIEW_ID, args = bundle)
        addToBackStack(null)
        setReorderingAllowed(true)
    }
}

fun Fragment.popThis() {
    parentFragmentManager.popBackStack()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
}
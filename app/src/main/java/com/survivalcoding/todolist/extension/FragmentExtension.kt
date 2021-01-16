package com.survivalcoding.todolist.extension

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.survivalcoding.todolist.R

inline fun <reified F : Fragment> Fragment.replaceTransactionWithAnimation(fragmentContainerViewId: Int, bundle: Bundle? = null) {
    parentFragmentManager.commit {
        setReorderingAllowed(true)
        setCustomAnimations(    // 새로 시작되는 Fragment : A, 기존 Fragment : B
            R.anim.fade_in,     // A 시작 Animation (enter)
            R.anim.fade_out,    // A 종료 Animation (exit)
            R.anim.fade_in,     // B 시작 Animation (popEnter)
            R.anim.fade_out,    // B 종료 Animation (popExit)
        )
        addToBackStack(null)
        replace(fragmentContainerViewId, F::class.java, bundle)
    }
}

fun Fragment.finish() = parentFragmentManager.popBackStack()
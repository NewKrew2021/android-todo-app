package com.survivalcoding.todolist.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import kotlin.reflect.KClass

fun Activity.navigateTo(cls: KClass<out Activity>, bundle: Bundle? = null) {
    val intent = Intent(this, cls.java)

    bundle?.let {
        intent.putExtras(it)
    }
    startActivity(intent)
}

fun Activity.navigateForResult(cls: KClass<out Activity>, bundle: Bundle? = null, requestCode: Int) {
    val intent = Intent(this, cls.java)

    bundle?.let {
        intent.putExtras(it)
    }
    startActivityForResult(intent, requestCode)
}

inline fun <reified T : Fragment> FragmentActivity.addTransaction(fragmentContainerViewId: Int, bundle: Bundle? = null) {
    supportFragmentManager.commit {
        setReorderingAllowed(true)
        add(fragmentContainerViewId, T::class.java, bundle)
    }
}


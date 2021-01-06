package com.survivalcoding.todolist.extension

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import kotlin.reflect.KClass

fun FragmentActivity.intentActionResult(kClass: KClass<out Activity>, requestCode: Int) {
    startActivityForResult(
        Intent(this, kClass.java), requestCode
    )
}


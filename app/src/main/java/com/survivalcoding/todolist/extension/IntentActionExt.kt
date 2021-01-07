package com.survivalcoding.todolist.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlin.reflect.KClass

fun FragmentActivity.intentActionResult(kClass: KClass<out Activity>, requestCode: Int) {
    startActivityForResult(
        Intent(this, kClass.java), requestCode
    )
}

fun FragmentActivity.intentActionResultWithBundle(
    kClass: KClass<out Activity>, extras: Bundle.() -> Unit = {}, requestCode: Int,
) {
    startActivityForResult(
        Intent(this, kClass.java).putExtras(Bundle().apply(extras)), requestCode
    )
}

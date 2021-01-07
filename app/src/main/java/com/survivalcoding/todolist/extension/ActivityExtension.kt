package com.survivalcoding.todolist.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
package com.survivalcoding.todolist.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlin.reflect.KClass

fun Context.intentActionWithBundle(kClass: KClass<out Activity>, extras: Bundle.() -> Unit = {}) {
    startActivity(
        Intent(this, kClass.java).putExtras(Bundle().apply(extras))
    )
}
package com.survivalcoding.todolist.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle

fun Activity.gotoActivity(it: Class<out Activity>, bundle: Bundle) {
    val intent = Intent(this, it)
    bundle.let {
        intent.putExtras(it)
    }
    startActivity(intent)
}

fun Activity.gotoActivityForResult(it: Class<out Activity>, bundle: Bundle, requestCode: Int) {
    val intent = Intent(this, it)
    bundle.let {
        intent.putExtras(bundle)
    }
    startActivityForResult(intent, requestCode)
}


package com.survivalcoding.todolist.todo.util

import com.survivalcoding.todolist.todo.view.main.MainActivity
import java.util.*

object Util {
    fun getPresentDate() : Long{
        return Date().time - MainActivity.ONE_DAY_MILLISECONDS
    }
}
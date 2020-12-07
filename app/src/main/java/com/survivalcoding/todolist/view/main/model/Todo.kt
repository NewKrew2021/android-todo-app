package com.survivalcoding.todolist.view.main.model

import java.io.Serializable

data class Todo(
    var todo: String,
    var datetime: Long,
    var isDone: Boolean = false,
    var id: Int = 0,
): Serializable

package com.survivalcoding.todolist.view.main.model

data class TodoData(
    var text: String,
    var time: Long,
    var isChecked: Boolean = false,
    var isMarked: Boolean = false,
    var pid: Int = 0,
)
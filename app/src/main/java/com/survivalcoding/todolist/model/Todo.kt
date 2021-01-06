package com.survivalcoding.todolist.model

data class Todo (
    var title: String,
    var times: String,
    var isDone: Boolean = false,
    var isOption: Boolean = false,  // true : 수정 삭제 Visible, false : 수정 삭제 Invisible
)
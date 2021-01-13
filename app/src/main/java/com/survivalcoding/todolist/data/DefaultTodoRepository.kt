package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.viewModel.searchItem

interface DefaultTodoRepository {

    fun searching(pattern: String)

    fun makeSearchData(pattern: String)

    fun checkedComplete(pattern: String)

    fun checkingComplete(dataList: MutableList<searchItem>)

    fun checkedRemove(pattern: String)

    fun addItem(todo: String)
}
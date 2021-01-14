package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.viewModel.listItem
import com.survivalcoding.todolist.viewModel.searchItem

interface DefaultTodoRepository {


    fun addItem(listitem: listItem)

    fun removeItem(id: Int)

    fun updateItem(listitem: listItem)

}
package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.view.RecyclerAdapter
import com.survivalcoding.todolist.viewModel.listItem
import com.survivalcoding.todolist.viewModel.searchItem


class TodoRepository() : DefaultTodoRepository {

    var data = mutableListOf<listItem>()
    var searchData = mutableListOf<searchItem>()

    override fun getDataList(): MutableList<listItem> {
        return data
    }

    override fun getSearchDataList(): MutableList<searchItem> {
        return searchData
    }

    override fun addItem(listitem: listItem) {
        TODO("Not yet implemented")
    }

    override fun removeItem(id: Int) {
        TODO("Not yet implemented")
    }

    override fun updateItem(listitem: listItem) {
        TODO("Not yet implemented")
    }


}


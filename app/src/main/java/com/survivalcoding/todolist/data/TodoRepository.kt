package com.survivalcoding.todolist.data

import com.survivalcoding.todolist.view.RecyclerAdapter
import com.survivalcoding.todolist.viewModel.listItem
import com.survivalcoding.todolist.viewModel.searchItem

/*
class TodoRepository(val adapter: RecyclerAdapter) : DefaultTodoRepository {

    var data = mutableListOf<listItem>()
    var searchData = mutableListOf<searchItem>()

    override fun searching(pattern: String) {

        makeSearchData(pattern)
        adapter.notifyDataSetChanged()
        //dataUpdate()
    }

    override fun makeSearchData(pattern: String) {
        searchData.clear()

        for (i in 0..data.size - 1) {
            if (data[i].toDo.contains(pattern)) {
                searchData.add(searchItem(data[i], i))
            }
        }
    }

    override fun checkedComplete(pattern: String) {

        checkingComplete(searchData)

        var last_index = data.size - 1
        var index = 0
        for (i in 0..last_index) {
            if (data[index].complete == true) {
                data.add(last_index + 1, data[index])
                data.removeAt(index)
            } else {
                index += 1
            }
        }
        makeSearchData(pattern)
    }

    override fun checkingComplete(dataList: MutableList<searchItem>) {
        var tmp_size = dataList.size
        var index = 0
        for (i in 0..tmp_size - 1) {
            if (dataList[index].item.check == true) {

                dataList[index].item.check = false
                dataList[index].item.complete = true
                data[dataList[index].index].complete = true

                dataList.add(
                    dataList[index]
                )
                dataList.removeAt(index)
                adapter.notifyItemRemoved(index)

            } else {
                index += 1
            }
        }
        adapter.notifyItemRangeChanged(0, dataList.size)
    }

    override fun checkedRemove(pattern: String) {
        val tmp = mutableListOf<Int>()
        for (i in searchData.size - 1 downTo 0) {
            if (searchData[i].item.check == true) {
                tmp.add(searchData[i].index)
                adapter.notifyItemRemoved(i)
            }
        }
        tmp.sortBy { it }
        for (i in tmp.size - 1 downTo 0) {
            data.removeAt(tmp[i])
        }
        makeSearchData(pattern)
    }

    override fun addItem(todo: String) {
        TODO("Not yet implemented")
    }
}

 */
package com.survivalcoding.todolist.data.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.view.RecyclerAdapter
import com.survivalcoding.todolist.viewModel.listItem
import com.survivalcoding.todolist.viewModel.searchItem
import java.text.SimpleDateFormat

class TodoSqliteRepository(context: Context, val adapter: RecyclerAdapter) : DefaultTodoRepository {

    val dbHelper = TodoDbHelper(context)

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

    //
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
        val sdf = SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
        val date = System.currentTimeMillis()
        val currentDate = sdf.format(date)

        data.add(
            0,
            listItem(
                todo,
                currentDate,
                check = false,
                complete = false,
            )
        )
    }

    fun writeDatabase() {
        removeDatabase()

        val db = dbHelper.writableDatabase

        for (i in 0..data.size - 1) {
            val values = ContentValues().apply {
                put(TodoContract.TodoEntry.COLUMN_NAME_TODO, data[i].toDo)
                put(TodoContract.TodoEntry.COLUMN_NAME_TIME, data[i].time)
                put(TodoContract.TodoEntry.COLUMN_NAME_CHECKING, if (data[i].check) 1 else 0)
                put(TodoContract.TodoEntry.COLUMN_NAME_COMPLETE, if (data[i].complete) 1 else 0)
            }
            db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)

        }
    }

    fun readDatabase() {
        val db = dbHelper.readableDatabase

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        val projection =
            arrayOf(
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_NAME_TODO,
                TodoContract.TodoEntry.COLUMN_NAME_TIME,
                TodoContract.TodoEntry.COLUMN_NAME_CHECKING,
                TodoContract.TodoEntry.COLUMN_NAME_COMPLETE,
            )

// Filter results WHERE "title" = 'My Title'
        //val selection = "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} = ?"
        //val selectionArgs = arrayOf("My Title")

// How you want the results sorted in the resulting Cursor
        val sortOrder = "${TodoContract.TodoEntry.COLUMN_NAME_TIME} DESC"

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val todo = getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TODO))
                val time =
                    getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_TIME))
                val check =
                    getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_CHECKING))
                val complete =
                    getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_COMPLETE))

                data.add(
                    listItem(
                        todo,
                        time,
                        if (check == 1) true else false,
                        if (complete == 1) true else false
                    )
                )
            }
            close()
        }
        data = data.sortedWith(Comparator<listItem> { a, b ->
            when {
                a.check == false -> 1
                a.check == true -> -1
                else -> 0
            }
        }).toMutableList()

    }

    fun removeDatabase() {

        val db = dbHelper.writableDatabase

        db.execSQL("delete from ${TodoContract.TodoEntry.TABLE_NAME}")
    }

}
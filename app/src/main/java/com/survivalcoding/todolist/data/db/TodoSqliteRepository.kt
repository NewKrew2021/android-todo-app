package com.survivalcoding.todolist.data.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.view.RecyclerAdapter
import com.survivalcoding.todolist.viewModel.listItem
import com.survivalcoding.todolist.viewModel.searchItem
import java.text.SimpleDateFormat

class TodoSqliteRepository(context: Context) : DefaultTodoRepository {

    val dbHelper = TodoDbHelper(context)

    private val data = mutableListOf<listItem>()
    private val searchData = mutableListOf<searchItem>()

    fun getDataList(): MutableList<listItem> {
        return data
    }

    fun getSearchDataList(): MutableList<searchItem> {
        return searchData
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

        var index: Int = 0
        var i = 0
        while (i < data.size) {
            if (data[index].complete == true) {
                data.add(data[index])
                data.removeAt(index)
            } else {
                index += 1
            }
            i += 1
        }
    }

    fun removeDatabase() {

        val db = dbHelper.writableDatabase

        db.execSQL("delete from ${TodoContract.TodoEntry.TABLE_NAME}")
    }

}
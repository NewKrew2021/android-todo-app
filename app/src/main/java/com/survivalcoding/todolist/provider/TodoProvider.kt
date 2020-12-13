package com.survivalcoding.todolist.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.survivalcoding.todolist.data.db.TodoDbHelper
import com.survivalcoding.todolist.provider.TodoProvider.Companion.CONTENT_ALL
import com.survivalcoding.todolist.provider.TodoProvider.Companion.CONTENT_ITEM

private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI("com.survivalcoding.todolist.provider", "todo", CONTENT_ALL)
    addURI("com.survivalcoding.todolist.provider", "todo/#", CONTENT_ITEM)
}

class TodoProvider : ContentProvider() {


    private lateinit var dbHelper: TodoDbHelper


    override fun onCreate(): Boolean {
        dbHelper = TodoDbHelper(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var localSortOrder: String = sortOrder ?: ""
        var localSelection: String = selection ?: ""
        when (sUriMatcher.match(uri)) {
            CONTENT_ALL -> {
                if (localSortOrder.isEmpty()) {
                    localSortOrder = "_ID ASC"
                }
            }
            CONTENT_ITEM -> {
                localSelection += "_ID = ${uri.lastPathSegment}"
            }
            else -> {

            }
        }

        return dbHelper.query(projection, localSelection, localSortOrder)
    }

    override fun getType(uri: Uri): String? {
        return when (sUriMatcher.match(uri)) {
            CONTENT_ALL -> "vnd.android.cursor.dir/vnd.com.survivalcoding.todolist.provider.todo"
            CONTENT_ITEM -> "vnd.android.cursor.item/vnd.com.survivalcoding.todolist.provider.todo"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (sUriMatcher.match(uri)) {
            CONTENT_ALL -> {
                val insertId = dbHelper.insert(values)
                if (insertId > 0) {
                    val newUri = ContentUris.withAppendedId(CONTENT_URI, insertId)
                    return newUri
                }
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return -1
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return dbHelper.update(values, selection, selectionArgs)
    }

    companion object {
        val CONTENT_URI = Uri.parse("content://com.survivalcoding.todolist.provider/todo")

        const val CONTENT_ALL = 1
        const val CONTENT_ITEM = 2
    }
}
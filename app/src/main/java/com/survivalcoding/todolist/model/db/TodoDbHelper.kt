package com.survivalcoding.todolist.model.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class TodoDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Todo.db"

        private const val SQL_CREATE_ENTRIES = "CREATE TABLE " +
                "${TodoContract.TodoEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} TEXT, " +
                "${TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED} INTEGER, " +
                "${TodoContract.TodoEntry.COLUMN_NAME_TIME_STAMP} TEXT)"

        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${TodoContract.TodoEntry.TABLE_NAME}"
    }
}
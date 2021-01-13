package com.survivalcoding.todolist.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.survivalcoding.todolist.data.db.TodoContract.TodoEntry.TABLE_NAME

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
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_ENTRIES =
            """CREATE TABLE $TABLE_NAME (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                ${TodoContract.TodoEntry.COLUMN_NAME_TEXT} TEXT,
                ${TodoContract.TodoEntry.COLUMN_NAME_TIME} INTEGER,
                ${TodoContract.TodoEntry.COLUMN_NAME_IS_DONE} INTEGER)"""

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
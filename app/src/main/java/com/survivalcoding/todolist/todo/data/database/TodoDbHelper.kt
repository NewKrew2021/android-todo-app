package com.survivalcoding.todolist.todo.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.survivalcoding.todolist.todo.data.database.TodoContract.TodoEntry.COLUMN_NAME_DUE_DATE
import com.survivalcoding.todolist.todo.data.database.TodoContract.TodoEntry.COLUMN_NAME_IS_DONE
import com.survivalcoding.todolist.todo.data.database.TodoContract.TodoEntry.COLUMN_NAME_TITLE
import com.survivalcoding.todolist.todo.data.database.TodoContract.TodoEntry.COLUMN_NAME_WRITE_TIME
import com.survivalcoding.todolist.todo.data.database.TodoContract.TodoEntry.TABLE_NAME

class TodoDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE =
            """CREATE TABLE $TABLE_NAME (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                ${COLUMN_NAME_IS_DONE} INTEGER,
                ${COLUMN_NAME_TITLE} TEXT,
                ${COLUMN_NAME_DUE_DATE} INTEGER,
                ${COLUMN_NAME_WRITE_TIME} INTEGER)"""

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"
    }
}
package com.survivalcoding.todolist.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class TodoDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TodoList.db"
        private const val SQL_CREATE_ENTRIES =
            """CREATE TABLE ${TodoContract.TodoEntry.TABLE_NAME} (
                    ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    ${TodoContract.TodoEntry.COLUMN_NAME_TITLE} TEXT,
                    ${TodoContract.TodoEntry.COLUMN_NAME_TIMESTAMP} INTEGER,
                    ${TodoContract.TodoEntry.COLUMN_NAME_IS_CHECKED} INTEGER)"""

        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${TodoContract.TodoEntry.TABLE_NAME}"
    }
}
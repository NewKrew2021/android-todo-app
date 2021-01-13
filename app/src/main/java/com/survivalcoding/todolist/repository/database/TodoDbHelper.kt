package com.survivalcoding.todolist.repository.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


class TodoDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Todo.db"
        private const val SQL_CREATE_ENTRIES =
                """CREATE TABLE ${TodoContract.TodoEntry.TABLE_NAME} (
                    ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    ${TodoContract.TodoEntry.COLUMN_NAME_TITLE} TEXT,
                    ${TodoContract.TodoEntry.COLUMN_NAME_DATE} INTEGER,
                    ${TodoContract.TodoEntry.COLUMN_NAME_DONE} INTEGER,
                    ${TodoContract.TodoEntry.COLUMN_NAME_MARK} INTEGER
                    )
                    """
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TodoContract.TodoEntry.TABLE_NAME}"
    }
}
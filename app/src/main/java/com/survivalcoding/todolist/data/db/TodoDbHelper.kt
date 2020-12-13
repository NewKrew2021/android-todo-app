package com.survivalcoding.todolist.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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

    fun query(projection: Array<out String>?, selection: String?, sortOrder: String?): Cursor? {
        val db = readableDatabase

        val cursor = db.query(
            TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
        return cursor
    }

    fun insert(values: ContentValues?): Long {
        val db = writableDatabase
        val newRowId = db?.insert(TABLE_NAME, null, values) ?: -1
        return newRowId
    }

    fun update(values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = writableDatabase

        val count = db.update(
            TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
        return count
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        val db = writableDatabase

        val deletedRows = db.delete(TABLE_NAME, selection, selectionArgs)
        return deletedRows
    }

    companion object {
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_ENTRIES =
            """CREATE TABLE $TABLE_NAME (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                ${TodoContract.TodoEntry.COLUMN_NAME_TODO} TEXT,
                ${TodoContract.TodoEntry.COLUMN_NAME_DATETIME} INTEGER,
                ${TodoContract.TodoEntry.COLUMN_NAME_IS_DONE} INTEGER)"""

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
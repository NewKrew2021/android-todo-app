package com.todolist.room.data.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.todolist.room.data.model.TodoItem

@Database(entities = [TodoItem::class], version = 2, exportSchema = false)
abstract class TodoDb : RoomDatabase() {

    abstract fun todoDao() : TodoDao
}
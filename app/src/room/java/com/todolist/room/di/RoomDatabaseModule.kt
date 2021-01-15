package com.todolist.room.di

import androidx.room.Room
import com.todolist.room.data.db.TodoDb
import org.koin.dsl.module


val roomDataBaseModule = module {
    single {
        Room.databaseBuilder(get(), TodoDb::class.java, "todo.db")
            .fallbackToDestructiveMigration().build()
    }

    single { get<TodoDb>().todoDao() }
}
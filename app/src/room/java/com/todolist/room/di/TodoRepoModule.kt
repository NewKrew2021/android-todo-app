package com.todolist.room.di

import com.todolist.room.data.repository.TodoRepoImpl
import org.koin.dsl.module


val repositoryModule = module {
    single { TodoRepoImpl(get()) }
}
package com.todolist.sqlite.di

import com.todolist.sqlite.data.repository.TodoRepoImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val repositoryModule = module {
    single { TodoRepoImpl(androidContext()) }
}
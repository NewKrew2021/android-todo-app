package com.survivalcoding.todolist.di

import com.survivalcoding.todolist.data.repository.TodoRepoImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val repositoryModule = module {
    single { TodoRepoImpl(androidContext()) }
}
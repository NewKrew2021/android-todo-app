package com.todolist.sqlite

import android.app.Application
import com.todolist.sqlite.di.repositoryModule
import com.todolist.sqlite.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TodoApplication)
            modules(viewModelModule)
            modules(repositoryModule)
        }
    }
}
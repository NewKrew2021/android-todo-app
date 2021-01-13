package com.todolist.room

import android.app.Application
import com.todolist.room.di.repositoryModule
import com.todolist.room.di.viewModelModule
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
package com.survivalcoding.todolist

import android.app.Application
import com.survivalcoding.todolist.di.repositoryModule
import com.survivalcoding.todolist.di.viewModelModule
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
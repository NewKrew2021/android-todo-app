package com.survivalcoding.todolist.di

import com.survivalcoding.todolist.ui.viewmodel.AddTodoViewModel
import com.survivalcoding.todolist.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { AddTodoViewModel() }
}
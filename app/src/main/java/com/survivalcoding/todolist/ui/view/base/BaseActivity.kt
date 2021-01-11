package com.survivalcoding.todolist.ui.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding, R : ViewModel> : AppCompatActivity() {

    lateinit var binding: T

    abstract val viewModel: R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStartView()
        afterStartView()
    }

    abstract fun initStartView()

    abstract fun afterStartView()
}
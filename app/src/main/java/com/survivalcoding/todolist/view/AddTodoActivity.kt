package com.survivalcoding.todolist.view

import com.survivalcoding.todolist.databinding.ActivityAddTodoBinding

class AddTodoActivity : BaseActivity<ActivityAddTodoBinding>() {

    override fun initStartView() {
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun afterStartView() {
        //
    }

}
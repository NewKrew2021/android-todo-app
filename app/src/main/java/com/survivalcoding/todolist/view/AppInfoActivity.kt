package com.survivalcoding.todolist.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R

class AppInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)
        setResult(RESULT_OK, Intent().apply { putExtra(INFO_KEY, DEVELOPER_INFO) })
    }

    companion object {
        const val INFO_KEY = "developer_info"
        const val DEVELOPER_INFO = "개발자: tony.mb"
    }
}
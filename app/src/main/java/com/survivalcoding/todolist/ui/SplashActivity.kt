package com.survivalcoding.todolist.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.databinding.ActivitySplashBinding
import com.survivalcoding.todolist.ui.main.MainActivity


class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            loadProgress()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }.start()
    }

    private fun loadProgress() {
        (1..100).forEach {
            Thread.sleep(20)
            binding.progressBar.progress = it
        }
    }
}
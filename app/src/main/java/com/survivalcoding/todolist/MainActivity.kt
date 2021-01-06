package com.survivalcoding.todolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button1.setOnClickListener {

            var intent = Intent(this, ListActivity::class.java)

            startActivity(intent)
        }
    }
}
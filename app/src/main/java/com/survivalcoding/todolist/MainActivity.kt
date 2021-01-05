package com.survivalcoding.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var button1 = findViewById<Button>(R.id.button1)
        var editText1 = findViewById<TextView>(R.id.editText1)


        button1.setOnClickListener {

            MyAdapter.data.add(editText1.text.toString())
            editText1.text=""
            var intent = Intent(this, ListActivity::class.java)

            startActivity(intent)
        }
    }
}
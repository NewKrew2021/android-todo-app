package com.survivalcoding.todolist

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val listView1 = findViewById<ListView>(R.id.listView1)


        listView1.adapter = MyAdapter()

    }
}
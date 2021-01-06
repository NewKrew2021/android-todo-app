package com.survivalcoding.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = MyAdapterRecycler()

        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        binding.button2.setOnClickListener {

            adapter.data.add(0, binding.editText.text.toString())
            binding.RecyclerView.adapter = adapter
        }


    }
}
package com.survivalcoding.todolist.ui.main

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoItem
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TodoListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 첫 실행 판별
        if (savedInstanceState == null) {
            //
        } else {

        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            addItem()
        }

        adapter = TodoListAdapter(this)

        binding.list.adapter = this.adapter
        binding.list.addItemDecoration(VerticalSpaceItemDecoration(ITEM_VERTICAL_INTERVAL))
    }

    // 상태 저장
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
        outState.putParcelableArrayList("data", adapter.list as ArrayList<TodoItem>)
    }

    // 상태 복원
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState: ${
            savedInstanceState.getString("test")
        }")
        val data = savedInstanceState.getParcelableArrayList<TodoItem>("data")
        data?.let {
            adapter.list.clear()
            adapter.list.addAll(it.toMutableList())
            adapter.notifyDataSetChanged()
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(MainActivity::class.java.simpleName, "onStart: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    private fun addItem() {

        if (binding.input.text.toString().isNotEmpty()) {
            adapter.addItem(
                    TodoItem(
                            binding.input.text.toString(),
                            false,
                            Calendar.getInstance().timeInMillis
                    )
            ).also {
                binding.input.text = null
                binding.list.layoutManager?.scrollToPosition(0)
            }
        } else {
            Toast.makeText(this, NO_CONTENT_MESSAGE, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ITEM_VERTICAL_INTERVAL = 12
        private const val NO_CONTENT_MESSAGE = "내용을 입력해주세요."
        val TAG = MainActivity::class.java.simpleName
    }

}
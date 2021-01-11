package com.survivalcoding.todolist.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.view.edit.EditActivity
import com.survivalcoding.todolist.view.main.adapter.TodoRecyclerViewAdapter
import com.survivalcoding.todolist.view.main.model.TodoData
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TodoRecyclerViewAdapter
    var pid = 0
    private fun itemClickListener(item: TodoData) {
        val intent = Intent(this, EditActivity::class.java).apply {
            putExtra(TODO_ITEM, item)
        }
        startActivityForResult(intent, EDIT_MAIN_REQ_CODE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        adapter = TodoRecyclerViewAdapter(itemClickListener = ::itemClickListener)
        binding.recyclerView.adapter = adapter
        var isMarked = false
        binding.markBox.setOnClickListener {
            isMarked = !isMarked
            if (isMarked) binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
        fun clickAction() {
            //To-Do 항목 추가
            if (binding.editTodo.text.isEmpty()) {
                Toast.makeText(this, ALERT_RENAME, Toast.LENGTH_SHORT).show()
            } else {
                adapter.addItem(
                    TodoData(
                        binding.editTodo.text.toString(),
                        Calendar.getInstance().timeInMillis,
                        isMarked = isMarked,
                        pid = pid++,
                    )
                )
                binding.editTodo.text.clear()
                binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
                isMarked = false

                //키보드 비활성화
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
        binding.buttonAdd.setOnClickListener {
            clickAction()
        }
        binding.editTodo.setOnEditorActionListener { _, actionId, event ->
            //엔터키 입력시 자동으로 추가
            if (actionId == EditorInfo.IME_ACTION_DONE) clickAction() //모바일기기의 엔터키
            else event.let { if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) clickAction() } //키보드에서의 엔터키
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(DATA_SAVE, adapter.items as ArrayList<TodoData>)
        outState.putInt(PID_SAVE, pid)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedData = savedInstanceState.getParcelableArrayList<TodoData>(DATA_SAVE)
        savedData?.let { adapter.addAllItems(savedData.toList()) }
        pid = savedInstanceState.getInt(PID_SAVE)
    }

    companion object {
        private const val DATA_SAVE = "todo"
        private const val PID_SAVE = "pid"
        private const val ALERT_RENAME = "내용을 입력해주세요."
        const val TODO_ITEM = "todo_pid"
        private const val EDIT_MAIN_REQ_CODE = 1
        private val TAG by lazy { MainActivity::class.java.simpleName }
    }
}
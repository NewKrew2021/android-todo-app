package com.survivalcoding.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoRecyclerViewAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import java.util.*

data class TodoData(
    var text: String,
    var time: Long,
    var isChecked: Boolean = false,
    var isMarked: Boolean = false,
    var pid: Int = 0,
)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var pid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val todoRecyclerViewAdapter = TodoRecyclerViewAdapter()
        binding.recyclerView.adapter = todoRecyclerViewAdapter
        var isMarked = false
        binding.markBox.setOnClickListener {
            isMarked = !isMarked
            if (isMarked) binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
        fun clickAction() {
            //To-Do 항목 추가
            if (binding.editTodo.text.isEmpty()) {
                Toast.makeText(this, "입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                todoRecyclerViewAdapter.addItem(
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
        binding.editTodo.setOnEditorActionListener { _, _, event ->
            //엔터키 입력시 자동으로 추가
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) clickAction()
            true
        }
    }
}
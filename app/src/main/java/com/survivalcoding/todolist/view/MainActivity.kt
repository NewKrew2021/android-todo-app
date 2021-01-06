package com.survivalcoding.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.CustomAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import java.util.*

data class TodoData(
    var text: String,
    var time: Long,
    var check: Boolean = false,
    var mark: Boolean = false
)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val customAdapter = CustomAdapter()
        var markBool = false
        binding.listView.adapter = customAdapter
        binding.markBox.setOnClickListener {
            markBool = !markBool
            if (markBool) binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
        fun clickAction() {
            //To-Do 항목 추가
            if (binding.editTodo.text.isEmpty()) {
                Toast.makeText(this, "입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                customAdapter.addItem(
                    TodoData(
                        binding.editTodo.text.toString(),
                        Calendar.getInstance().timeInMillis,
                        mark = markBool
                    )
                )
                binding.editTodo.text.clear()
                binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
                markBool = false

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
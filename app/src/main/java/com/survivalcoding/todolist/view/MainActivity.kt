package com.survivalcoding.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.CustomAdapter
import java.util.*

data class TodoData(
    var text: String,
    var time: Long,
    var check: Boolean = false,
    var mark: Boolean = false
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<ListView>(R.id.list_view)
        val customAdapter = CustomAdapter()
        var markBool = false
        listView.adapter = customAdapter
        val markBox = findViewById<ImageView>(R.id.mark_box)
        markBox.setOnClickListener {
            markBool = !markBool
            if (markBool) markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
        val addButton = findViewById<ImageView>(R.id.button_add)
        val editTodo = findViewById<EditText>(R.id.edit_todo)
        fun clickAction() {
            //To-Do 항목 추가
            if (editTodo.text.isEmpty()) {
                Toast.makeText(this, "입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                customAdapter.addItem(
                    TodoData(
                        editTodo.text.toString(),
                        Calendar.getInstance().timeInMillis,
                        mark = markBool
                    )
                )
                editTodo.text.clear()
                markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
                markBool = false

                //키보드 비활성화
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
        addButton.setOnClickListener {
            clickAction()
        }
        editTodo.setOnEditorActionListener { _, _, event ->
            //엔터키 입력시 자동으로 추가
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) clickAction()
            true
        }

    }
}
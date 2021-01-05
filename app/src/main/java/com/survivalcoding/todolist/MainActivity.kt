package com.survivalcoding.todolist

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        var mark_bool = false
        listView.adapter = customAdapter
        val markBox = findViewById<ImageView>(R.id.mark_box)
        markBox.setOnClickListener {
            mark_bool = !mark_bool
            if (mark_bool) markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
        val addButton = findViewById<ImageView>(R.id.button_add)
        val editTodo = findViewById<EditText>(R.id.edit_todo)
        fun clickAction() {
            if (editTodo.text.isEmpty()) {
                Toast.makeText(this, "입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                customAdapter.addItem(
                    TodoData(
                        editTodo.text.toString(),
                        Calendar.getInstance().timeInMillis,
                        mark = mark_bool
                    )
                )
                editTodo.text.clear()
                markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
                mark_bool = false
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
        addButton.setOnClickListener {
            clickAction()
        }
        editTodo.setOnEditorActionListener { _, _, event ->
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) clickAction()
            true
        }

    }
}
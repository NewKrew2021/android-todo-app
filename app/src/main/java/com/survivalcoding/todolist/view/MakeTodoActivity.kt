package com.survivalcoding.todolist.view

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMakeTodoBinding
import com.survivalcoding.todolist.model.TodoItem
import java.text.SimpleDateFormat
import java.util.*

class MakeTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        val binding = ActivityMakeTodoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val mode = intent.extras?.get(MainActivity.MODE)
        lateinit var oldItem: TodoItem
        if (mode == MainActivity.ACTIVITY_EDIT_MODE) {
            oldItem = intent.extras?.get(MainActivity.ITEM) as TodoItem
            binding.apply {
                titleEdit.setText(oldItem.title)
                dateView.text = String.format("%d-%02d-%02d", oldItem.date.get(Calendar.YEAR), oldItem.date.get(Calendar.MONTH) + 1, oldItem.date.get(Calendar.DAY_OF_MONTH))
                addTodoButton.text = "수정"
            }
            //modify mode 인 경우
        }

        binding.apply {
            dateView.setOnClickListener {
                datePickerShow(dateView)
            }
            addTodoButton.setOnClickListener {
                if (titleEdit.text.isEmpty()) {
                    Toast.makeText(this@MakeTodoActivity, "할일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    if (dateView.text.toString() == "yyyy - mm - dd") {
                        Toast.makeText(this@MakeTodoActivity, "기한을 선택해주세요", Toast.LENGTH_SHORT).show()
                    } else {
                        val calendar = Calendar.getInstance()
                        calendar.time = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(dateView.text.toString())?: Date(Calendar.getInstance().timeInMillis)
                        lateinit var data: Intent
                        if (mode == MainActivity.ACTIVITY_EDIT_MODE) {
                            data = Intent().apply {
                                putExtra("TodoItem", TodoItem(title = titleEdit.text.toString(),
                                        date = calendar,
                                        isComplete = false,
                                        isMark = oldItem.isMark,
                                        id = oldItem.id))
                            }
                        } else {
                            data = Intent().apply {
                                putExtra("TodoItem", TodoItem(title = titleEdit.text.toString(),
                                        date = calendar,
                                        isComplete = false,
                                        isMark = true))
                            }
                        }
                        setResult(Activity.RESULT_OK, data)
                        finish()
                    }
                }
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun datePickerShow(txtView: TextView) {
        val currentTime = Calendar.getInstance()
        val year = currentTime.get(Calendar.YEAR)
        val month = currentTime.get(Calendar.MONTH)
        val day = currentTime.get(Calendar.DAY_OF_MONTH)
        val datePicker = DatePickerDialog(this@MakeTodoActivity, R.style.SpinnerDatePickerStyle, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                txtView.text = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
            }
        }, year, month, day + 1);

        datePicker.show()
    }

}
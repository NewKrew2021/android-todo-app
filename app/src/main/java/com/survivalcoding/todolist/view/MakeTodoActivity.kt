package com.survivalcoding.todolist.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMakeTodoBinding
import java.util.*

class MakeTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        val binding = ActivityMakeTodoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {
            dateView.setOnClickListener {
                datePickerShow(dateView)
            }
            addTodoButton.setOnClickListener {
                if (titleEdit.text.isEmpty()) {
                    Toast.makeText(this@MakeTodoActivity, "할일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    TODO("시간이 비었을때와 아닐때로 분기")
                }
            }
        }


    }

    private fun datePickerShow(txtView: TextView) {
        val currentTime = Calendar.getInstance()
        val year = currentTime.get(Calendar.YEAR)
        val month = currentTime.get(Calendar.MONTH)
        val day = currentTime.get(Calendar.DAY_OF_MONTH)
        val datePicker = DatePickerDialog(this@MakeTodoActivity, R.style.SpinnerDatePickerStyle, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                txtView.text = String.format("%d - %02d - %02d", year, month + 1, dayOfMonth)
            }
        }, year, month, day + 1);

        datePicker.show()
    }
}
package com.survivalcoding.todolist.view.edit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityEditBinding
import com.survivalcoding.todolist.view.main.MainActivity.Companion.TODO_ITEM
import com.survivalcoding.todolist.view.main.model.TodoData

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val item: TodoData? = intent.getParcelableExtra(TODO_ITEM)
        ///받아 오는 item 확인
        Log.d(TAG, item.toString())
        item?.let {
            binding.editTodo.setText(item.text)
            binding.checkBox.isChecked = item.isChecked
            if (item.isMarked) binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }

        // Todo xml 에서 수정하기, 삭제하기 버튼 추가, 수정 시 수정 내용 MainActivity로 전달

    }

    companion object {
        private val TAG by lazy { EditActivity::class.java.simpleName }
    }
}
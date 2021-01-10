package com.survivalcoding.todolist.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityListBinding
import com.survivalcoding.todolist.databinding.Dialog1Binding
import com.survivalcoding.todolist.viewModel.listItem
import java.text.SimpleDateFormat


class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var bindingDialog: Dialog1Binding

    private lateinit var adapter: RecyclerAdapter

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




        var builder = AlertDialog.Builder(this)
        adapter =
            RecyclerAdapter() { adapter: RecyclerAdapter, position: Int ->

                val dialogView = layoutInflater.inflate(R.layout.dialog1, null)

                builder.setView(dialogView).setTitle("수정사항을 입력하세요")
                    .setPositiveButton("확인") { dialogInterface, i ->

                        dialogSetting(dialogView, adapter, position)

                    }
                    .setNegativeButton("취소") { dialogInterface, i ->

                    }
                    .show()
            }

        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)


        //binding.RecyclerView.addItemDecoration(dividerItemDecoration)
        binding.addButton.setOnClickListener {
            addButtonListener(adapter, binding)
        }

        binding.editText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) addButtonListener(
                    adapter,
                    binding
                )
                else if (keyCode == KeyEvent.KEYCODE_BACK) finish()
            }
            true
        }
        binding.removeButton.setOnClickListener {
            adapter.checkedRemove(binding.searchEditText.text.toString())
        }

        binding.completeButton.setOnClickListener {

            adapter.checkedComplete(binding.searchEditText.text.toString())
        }
        binding.searchEditText.addTextChangedListener {
            adapter.searching(binding.searchEditText.text.toString())
        }
    }

    fun dialogSetting(dialogView: View, adapter: RecyclerAdapter, position: Int) {
        var reviseText = dialogView.findViewById<EditText>(R.id.reviseText)

        var tmpString = reviseText.text.toString()
        val sdf = SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
        val date = System.currentTimeMillis()
        val currentDate = sdf.format(date)

        adapter.data.add(
            0,
            listItem(
                tmpString,
                currentDate,
                false,
                false,
            )
        )
        adapter.data.removeAt(adapter.searchData[position].index + 1)

        //adapter.notifyItemInserted(0)
        //adapter.data.removeAt(position + 1)
        //adapter.notifyItemRemoved(position+1)

        //adapter.notifyItemRangeChanged(0, adapter.data.size)
        adapter.searching(binding.searchEditText.text.toString())
    }

    fun addButtonListener(adapter: RecyclerAdapter, binding: ActivityListBinding) {
        val sdf = java.text.SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
        val date = System.currentTimeMillis()
        val currentDate = sdf.format(date)

        adapter.data.add(
            0,
            listItem(
                binding.editText.text.toString(),
                currentDate,
                false,
                false,
            )
        )
        //adapter.dataUpdate()
        //adapter.notifyItemInserted(0)
        //adapter.notifyItemRangeChanged(0,adapter.data.size)
        binding.editText.setText("")
        adapter.searching(binding.searchEditText.text.toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("data", adapter.data as ArrayList<listItem>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val data = savedInstanceState.getParcelableArrayList<listItem>("data")
        data?.let {
            adapter.data = data
            adapter.notifyDataSetChanged()
        }
    }
}
//오류 검토 완료
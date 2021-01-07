package com.survivalcoding.todolist.view

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityListBinding
import com.survivalcoding.todolist.databinding.Dialog1Binding
import com.survivalcoding.todolist.viewModel.ListActivityVM
import com.survivalcoding.todolist.viewModel.MyAdapterRecycler

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var bindingDialog: Dialog1Binding

    private lateinit var adapter: MyAdapterRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var builder = AlertDialog.Builder(this)
        adapter =
            MyAdapterRecycler() { adapter: MyAdapterRecycler, position: Int ->

                val dialogView = layoutInflater.inflate(R.layout.dialog1, null)

                builder.setView(dialogView).setTitle("수정사항을 입력하세요")
                    .setPositiveButton("확인") { dialogInterface, i ->

                        ListActivityVM.dialogSetting(dialogView, adapter, position)

                    }
                    .setNegativeButton("취소") { dialogInterface, i ->

                    }
                    .show()
            }

        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration =
            DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        binding.RecyclerView.addItemDecoration(dividerItemDecoration)
        binding.addButton.setOnClickListener {
            ListActivityVM.addButtonListener(adapter, binding)
        }

        binding.editText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) ListActivityVM.addButtonListener(
                    adapter,
                    binding
                )
                else if (keyCode == KeyEvent.KEYCODE_BACK) finish()
            }
            true
        }
        binding.removeButton.setOnClickListener {
            adapter.checkedRemove()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        ListActivityVM.onSaveInstace(outState, adapter)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        ListActivityVM.onRestoreInstance(savedInstanceState, adapter)
    }


}

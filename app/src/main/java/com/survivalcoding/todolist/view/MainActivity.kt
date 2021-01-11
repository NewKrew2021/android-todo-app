package com.survivalcoding.todolist.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.extension.gotoActivityForResult
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.viewmodel.TodoViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var todoListAdapter: TodoListAdapter
    lateinit var binding: ActivityMainBinding
    val todoViewModel: TodoViewModel = TodoViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        todoListAdapter = TodoListAdapter(
                _completeListener = {
                    todoViewModel.complete(it)
                    updateList()
                },
                _modifyListener = {
                    val bundle = Bundle()
                    bundle.putString(MODE, ACTIVITY_EDIT_MODE)
                    bundle.putParcelable(ITEM, it)
                    gotoActivityForResult(MakeTodoActivity::class.java, bundle, EDIT_REQUEST_CODE)
                },
                _deleteListener = {
                    todoViewModel.remove(it)
                    updateList()
                },
                _markListener = {
                    todoViewModel.mark(it)
                    updateList()
                })
        binding.apply {
            todoListView.adapter = todoListAdapter
            mainLayout.setOnClickListener {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchEdit.windowToken, 0)
            }
            addButton.setOnClickListener {
                val bundle: Bundle = Bundle()
                bundle.putString(MODE, ACTIVITY_ADD_MODE)
                gotoActivityForResult(MakeTodoActivity::class.java, bundle, ADD_REQUEST_CODE)
            }
            searchEdit.addTextChangedListener(getTextWatcher())
        }

    }

    fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val searchList = todoViewModel.getItemList().filter {
                    it.title.contains(s.toString())
                }
                searchList.sortedWith(compareBy(
                        { it.isComplete },
                        { it.isMark },
                        { it.date }))
                todoListAdapter.submitList(searchList)
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("todoList", todoViewModel.items as ArrayList<TodoItem>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val todoList = savedInstanceState.getParcelableArrayList<TodoItem>("todoList")
        todoList?.let {
            todoViewModel.items = it
        }
        updateList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_REQUEST_CODE -> {
                    data?.getParcelableExtra<TodoItem>("TodoItem")?.let {
                        it.id = dataId.id
                        dataId.id += 1
                        todoViewModel.add(it)
                        updateList()
                    }
                }
                EDIT_REQUEST_CODE -> {
                    data?.getParcelableExtra<TodoItem>("TodoItem")?.let {
                        todoViewModel.modify(it)
                        updateList()
                    }
                }
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ADD_REQUEST_CODE = 100
        const val EDIT_REQUEST_CODE = 200
        const val MODE = "mode"
        const val ACTIVITY_ADD_MODE = "add"
        const val ACTIVITY_EDIT_MODE = "edit"
        const val ITEM = "item"
    }

    private fun updateList() {
        if (binding.searchEdit.text.isEmpty()) {
            todoListAdapter.submitList(todoViewModel.sort())
        } else { // 검색이 되어 있는 결과로
            todoListAdapter.submitList(todoListAdapter.currentList.sortedWith(compareBy(
                    { it.isComplete },
                    { it.isMark },
                    { it.date })))
        }
    }
}

object dataId {
    var id: Int = 0
}


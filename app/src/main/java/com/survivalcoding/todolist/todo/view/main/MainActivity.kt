package com.survivalcoding.todolist.todo.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuCompat
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.todo.data.TodoData
import com.survivalcoding.todolist.todo.view.main.adapter.TodoAdapter
import com.survivalcoding.todolist.todo.view.model.Todo
import java.util.*

class MainActivity : AppCompatActivity() {
    var orderMethod = ASCENDING
    lateinit var todoAdapter: TodoAdapter
    private val model = TodoData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(this, Date().time.toString(), Toast.LENGTH_LONG).show()
        val listBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(listBinding.root)

        todoAdapter = TodoAdapter()
        listBinding.todoList.adapter = todoAdapter
        listBinding.addTodoButton.setOnClickListener {
            // Todo AddActivity로 전환된 후, 추가할 Todo객체를 intent로 받아온다.
            val text = "simple text"
            todoAdapter.addTodo(Todo(false, text, Date().time))
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ROTATION_RESTORE_KEY, model.todoList as ArrayList<Todo>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val list = savedInstanceState.getParcelableArrayList<Todo>(ROTATION_RESTORE_KEY)
        list?.let { model.updateTodo(list) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_option, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sortToDate -> {
                todoAdapter.sortByDueDate(orderMethod)
                Toast.makeText(this, "날짜순 정렬", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.sortToTitle -> {
                todoAdapter.sortByTitle(orderMethod)
                Toast.makeText(this, "가나다순 정렬", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.ascending -> {
                Toast.makeText(this, "오름차순 정렬", Toast.LENGTH_SHORT).show()
                item.isChecked = true
                orderMethod = ASCENDING
                return true
            }
            R.id.descending -> {
                Toast.makeText(this, "내림차순 정렬", Toast.LENGTH_SHORT).show()
                item.isChecked = true
                orderMethod = DESCENDING
                return true
            }
        }
        return false
    }

    companion object {
        const val ROTATION_RESTORE_KEY = "listData"
        const val ASCENDING = 123456    // 1, 2이면 다른 값과 중복되지 않을까 해서 임의의 값을 넣었다.
        const val DESCENDING = 654321
        const val DATE_FORMAT = "yyyy-mm-dd"
        const val INTENT_KEY = "todo"
    }
}
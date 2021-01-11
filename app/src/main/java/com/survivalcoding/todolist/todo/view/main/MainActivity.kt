package com.survivalcoding.todolist.todo.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuCompat
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.todo.data.TodoData
import com.survivalcoding.todolist.todo.view.add.AddActivity
import com.survivalcoding.todolist.todo.view.main.adapter.TodoAdapter
import com.survivalcoding.todolist.todo.view.model.Todo
import java.util.*

class MainActivity : AppCompatActivity() {
    var orderMethod = ASCENDING
    lateinit var todoAdapter: TodoAdapter
    private val model = TodoData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(listBinding.root)

        // edit button으로 사용 예정
        val textClickEvent = { Toast.makeText(this, "text clicked", Toast.LENGTH_SHORT).show()}

        todoAdapter = TodoAdapter(textClickEvent)
        listBinding.todoList.adapter = todoAdapter
        listBinding.addTodoButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST_QUEUE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ROTATION_RESTORE_KEY, model.todoList as ArrayList<Todo>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val list = savedInstanceState.getParcelableArrayList<Todo>(ROTATION_RESTORE_KEY)
        list?.let { todoAdapter.updateTodo(list) }
    }

    // resultCode = AddActivity에서 setResult메소드로 넣어준 값
    // requestCode = MainActivity에서 intent시에 넣어준 두 번째 매개변수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ADD_REQUEST_QUEUE -> {
                    data?.getParcelableExtra<Todo>(INTENT_KEY)?.let {
                        todoAdapter.addTodo(it)
                    }
                }
            }
        }
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
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val ROTATION_RESTORE_KEY = "listData"
        const val ASCENDING = 123456    // 1, 2이면 다른 값과 중복되지 않을까 해서 임의의 값을 넣었다.
        const val DESCENDING = 654321
        const val DATE_FORMAT = "yyyy-mm-dd"
        const val INTENT_KEY = "todo"
        const val ADD_REQUEST_QUEUE = 100
        const val EDIT_REQUEST_QUEUE = 200
        const val ONE_DAY_MILLISECONDS = 24 * 60 * 60 * 1000 // 86_400_000
    }
}
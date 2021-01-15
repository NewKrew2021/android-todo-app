package com.survivalcoding.todolist.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.data.db.TodoSqliteRepository
import com.survivalcoding.todolist.databinding.ActivityListBinding
import com.survivalcoding.todolist.databinding.Dialog1Binding
import com.survivalcoding.todolist.factory.MyFragmentFactory
import com.survivalcoding.todolist.viewModel.listItem
import java.text.SimpleDateFormat


class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    private lateinit var adapter: RecyclerAdapter
    var imm: InputMethodManager? = null

    //lateinit var todoRepository: TodoRepository
    lateinit var todoSqliteRepository: TodoSqliteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        todoSqliteRepository = TodoSqliteRepository(applicationContext)
        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?

        adapter =
            RecyclerAdapter(todoSqliteRepository) { adapter: RecyclerAdapter, position: Int ->

                EditDialogFragment({ dialogView: View ->
                    dialogSetting(
                        dialogView,
                        adapter,
                        position
                    )
                }).show(
                    supportFragmentManager, EditDialogFragment.TAG
                )

            }
        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

         todoSqliteRepository.readDatabase()

        adapter.getData(
            todoSqliteRepository.getDataList(),
            todoSqliteRepository.getSearchDataList()
        )

        adapter.searching("")

        binding.addButton.setOnClickListener {
            addButtonListener(adapter, binding)
        }

        binding.editText.setOnKeyListener { _, keyCode, event ->
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

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("key", "bye bye")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.addItem -> {
                var toast = Toast.makeText(this, "hello world", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.LEFT, 400, 400)
                toast.show()
                true
            }
            /*
            R.id.fragmentButton -> {

                supportFragmentManager.fragmentFactory = MyFragmentFactory()
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<FirstFragment>(R.id.fragment_container_view)
                    addToBackStack(null)
                }

                true
            }

             */
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    fun hideKeyboard(v: View) {
        if (v != null) {
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun dialogSetting(dialogView: View, adapter: RecyclerAdapter, position: Int) {
        val reviseText = dialogView.findViewById<EditText>(R.id.reviseText)

        var tmpString = reviseText.text.toString()
        val sdf = SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
        val date = System.currentTimeMillis()
        val currentDate = sdf.format(date)

        adapter.data.add(
            0,
            listItem(
                tmpString,
                currentDate,
                check = false,
                complete = false,
                id = adapter.searchData[position].item.id
            )
        )
        todoSqliteRepository.updateItem(
            listItem(
                tmpString,
                currentDate,
                check = false,
                complete = false,
                id = adapter.searchData[position].item.id
            )
        )

        adapter.data.removeAt(adapter.searchData[position].index + 1)


        //adapter.notifyItemRemoved(position+1)

        //adapter.notifyItemRangeChanged(0, adapter.data.size)
        adapter.notifyItemRemoved(position)
        adapter.makeSearchData(binding.searchEditText.text.toString())
        adapter.notifyItemRangeChanged(0, adapter.searchData.size)
    }

    fun addButtonListener(adapter: RecyclerAdapter, binding: ActivityListBinding) {

        adapter.addItem(binding.editText.text.toString())

        if (
            binding.editText.text.toString().contains(binding.searchEditText.text.toString())
        ) {
            adapter.notifyItemInserted(0)
        }
        adapter.notifyItemRangeChanged(0, adapter.data.size)

        binding.editText.setText("")
        adapter.makeSearchData(binding.searchEditText.text.toString())
        //adapter.dataUpdate()
        hideKeyboard(binding.root)


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
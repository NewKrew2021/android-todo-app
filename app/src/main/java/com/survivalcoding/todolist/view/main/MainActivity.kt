package com.survivalcoding.todolist.view.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.MainViewModel
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.util.dateToString
import com.survivalcoding.todolist.view.edit.EditActivity
import com.survivalcoding.todolist.view.main.adapter.TodoAdapter
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()

    private var actionMode: ActionMode? = null

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TodoAdapter(
            showToastMessageListener = { message: String -> showToastMessage(message) },
            removeClickListener = { todo -> viewModel.remove(todo) },
            updateListener = { updateUI() },
            editClickListener = { todo ->
                val intent = Intent(this, EditActivity::class.java).apply {
                    putExtra(TODO_KEY, todo)
                }
                startActivityForResult(intent, EDIT_ACTIVITY_REQ_CODE)
            },
            getActionMode = { getActionMode() },
            setActionBarTitle = { setActionBarTitle() },
        )

        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )

            buttonAdd.setOnClickListener {
                if (editTextTitle.text.trim().isNotEmpty()) {
                    viewModel.add(
                        Todo(
                            editTextTitle.text.toString(),
                            dateToString(Calendar.getInstance().time)
                        )
                    )
                    updateUI()

                    recyclerView.smoothScrollToPosition(0)
                    editTextTitle.text.clear()
                }
            }
            // SearchView 전체 클릭 시 활성화 Event
            searchView.setOnClickListener {
                searchView.isIconified = false
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                // Called when the user submits the query.
                // Keyboard Key press (enter key) or press submit button.
                // return false to let the SearchView handle the submission by launching any associated intent.
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                // Called when the query text is changed by the user.
                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        updateUI()
                    }
                    return true
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                EDIT_ACTIVITY_REQ_CODE -> {
                    data?.extras?.getParcelable<Todo>(TODO_KEY)?.let {
                        val isEdited = viewModel.edit(it)
                        if (isEdited) {
                            updateUI()
                        } else {
                            showToastMessage("일시적인 오류로 수정할 수 없습니다.")
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_mode_remove -> {
                actionMode = startSupportActionMode(removeModeCallback)
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(TODO_ID_KEY, viewModel.id.get())
        outState.putParcelableArrayList(TODO_STATE_KEY, viewModel.items as ArrayList<out Todo>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        viewModel.id.set(savedInstanceState.getInt(TODO_ID_KEY))
        savedInstanceState.getParcelableArrayList<Todo>(TODO_STATE_KEY)?.let {
            viewModel.addAll(it)
            updateUI()
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateUI() {
        val query = binding.searchView.query.toString()

        adapter.submitList(
            if (query.isEmpty()) viewModel.getOrderedItems()
            else viewModel.getOrderedWithFilteredItems(query)
        )
    }

    private fun getActionMode(): ActionMode? = actionMode

    private fun setActionBarTitle() {
        actionMode?.title = "${viewModel.getRemovablesCount()}개 선택"
    }

    private val removeModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.main_menu_remove, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.title = "${viewModel.getRemovablesCount()}개 선택"
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.menu_remove -> {
                    val isRemoved = viewModel.removeAllRemovable()

                    if (isRemoved) {
                        updateUI()
                        mode?.finish()
                    }
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            viewModel.clearAllRemovable()
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        const val TODO_ID_KEY = "TODO_ID_KEY"
        const val TODO_STATE_KEY = "TODO_STATE_KEY"
        const val TODO_TITLE_KEY = "TODO_TITLE_KEY"
        const val TODO_KEY = "TODO_KEY"

        const val EDIT_ACTIVITY_REQ_CODE = 100
    }
}
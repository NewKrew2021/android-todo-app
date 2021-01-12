package com.survivalcoding.todolist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.FragmentTodoBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.util.getCurrentTime
import com.survivalcoding.todolist.viewmodel.TodoViewModel

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TodoListAdapter
    private val viewModel by lazy {
        TodoViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        setOnListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            MainActivity.SAVE_INSTANCE_TODO_ITEM_KEY,
            viewModel.todoList as ArrayList<TodoItem>
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getParcelableArrayList<TodoItem>(MainActivity.SAVE_INSTANCE_TODO_ITEM_KEY)
            ?.let {
                viewModel.clearTodoList()
                it.forEach { todo ->
                    viewModel.addTodo(todo)
                }
                updateTodoList()
            }
    }

    private fun updateTodoList() {
        val sortOptions = resources.getStringArray(R.array.sort_options)
        // 시간 순으로 정렬
        if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[0]) {
            viewModel.sortByTime()
        }
        // 사전 순으로 정렬
        else if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[1]) {
            viewModel.sortByTitle()
        }
        adapter.submitList(viewModel.todoList.toList())
    }

    private fun setOnListener() {
        binding.apply {
            registerButton.setOnClickListener {
                toDoEditText.text.apply {
                    // 내용 없이 버튼을 눌렀을 때
                    if (isNullOrEmpty()) {
                        Toast.makeText(
                            context,
                            getString(R.string.empty_title_warning_message),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    // id 값을 ViewModel 에서 관리해주기 때문에 처음에 등록할 때는 NEW_TODO_TASK 로 지정
                    viewModel.addTodo(
                        TodoItem(
                            NEW_TODO_TASK,
                            false,
                            toString(),
                            getCurrentTime()
                        )
                    )
                    clear()
                    hideKeyboard()
                }
                updateTodoList()
            }
            sortButton.setOnClickListener {
                updateTodoList()
            }
            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                // 현재는 구현할 필요없음
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    adapter.submitList(viewModel.searchTodoItem(newText))
                    return true
                }
            })
        }
    }

    private fun initializeView() {
        adapter = TodoListAdapter(
            checkTodoListener = {
                updateTodoList()
            },
            editTodoListener = { todoItem, newTodoTitle ->
                viewModel.updateTodo(todoItem, newTodoTitle)
                updateTodoList()
            },
            removeTodoListener = {
                viewModel.removeTodo(it)
                updateTodoList()
            }
        )
        binding.toDoList.apply {
            this.adapter = this@TodoFragment.adapter
        }
        // Spinner 생성
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sort_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.sortOptionSpinner.adapter = adapter
        }
    }

    private fun hideKeyboard() {
        val manager: InputMethodManager =
            activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NEW_TODO_TASK = -1
    }
}
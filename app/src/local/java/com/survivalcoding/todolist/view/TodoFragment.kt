package com.survivalcoding.todolist.view

import android.os.Bundle
import android.view.*
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
import com.survivalcoding.todolist.model.TodoRepository
import com.survivalcoding.todolist.util.getCurrentTime
import com.survivalcoding.todolist.util.replaceTransactionWithAnimation

class TodoFragment(private val repository: TodoRepository) : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        updateTodoList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val list = mutableListOf<TodoItem>()
        repository.getTodoListSortedByTime().forEach {
            list.add(it)
        }
        if (list.isNotEmpty()) {
            outState.putParcelableArrayList(
                SAVE_INSTANCE_TODO_ITEM_KEY,
                list as ArrayList<TodoItem>
            )
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getParcelableArrayList<TodoItem>(SAVE_INSTANCE_TODO_ITEM_KEY)
            ?.let {
                repository.clearTodoList()
                it.forEach { todo ->
                    repository.addTodo(todo)
                }
                updateTodoList()
            }
    }

    private fun updateTodoList() {
        val sortOptions = resources.getStringArray(R.array.sort_options)
        // 시간 순으로 정렬
        if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[0]) {
            adapter.submitList(repository.getTodoListSortedByTime())
        }
        // 사전 순으로 정렬
        else if (binding.sortOptionSpinner.selectedItem.toString() == sortOptions[1]) {
            adapter.submitList(repository.getTodoListSortedByTitle())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_app -> {
                replaceTransactionWithAnimation<AppInfoFragment>(R.id.fragment_container_view)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setOnListener() {
        binding.apply {
            registerButton.setOnClickListener {
                toDoEditText.text.apply {
                    // 내용 없이 버튼을 눌렀을 때
                    if (isNullOrEmpty()) {
                        showToast(getString(R.string.empty_title_warning_message))
                        return@setOnClickListener
                    }
                    // id 값을 ViewModel 에서 관리해주기 때문에 처음에 등록할 때는 NEW_TODO_TASK 로 지정
                    repository.addTodo(TodoItem(NEW_TODO_TASK, false, toString(), getCurrentTime()))
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
                    if (newText.isEmpty()) {
                        updateTodoList()
                    } else {
                        adapter.submitList(repository.searchTodoItem(newText))
                        /*
                         * 검색어 하이라이팅을 위해 호출.
                         * 윗 줄에서 submitList 하고 함수 내부에서 notifyDataSetChanged 도 호출하기 때문에 좋은 방식은 아닌 것 같음.
                         */
                        adapter.setSearchKeyword(newText)
                    }
                    return true
                }
            })
        }
    }

    private fun initializeView() {
        adapter = TodoListAdapter(
            checkTodoListener = { item, isChecked ->
                repository.checkTodo(item, isChecked)
                updateTodoList()
            },
            editTodoListener = { todoItem, newTodoTitle ->
                repository.updateTodo(todoItem, newTodoTitle)
                updateTodoList()
            },
            removeTodoListener = { item ->
                RemoveCheckDialog(item) {
                    repository.removeTodo(item)
                    updateTodoList()
                }.show(childFragmentManager, RemoveCheckDialog.TAG)
            }
        )
        binding.toDoList.adapter = adapter

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

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val SAVE_INSTANCE_TODO_ITEM_KEY = "todoList"
        const val NEW_TODO_TASK = -1
    }
}
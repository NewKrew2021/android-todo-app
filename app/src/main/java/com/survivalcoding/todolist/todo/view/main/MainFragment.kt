package com.survivalcoding.todolist.todo.view.main

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.todo.data.TodoData
import com.survivalcoding.todolist.todo.view.MainActivity
import com.survivalcoding.todolist.todo.view.add.AddFragment
import com.survivalcoding.todolist.todo.view.edit.EditFragment
import com.survivalcoding.todolist.todo.view.main.adapter.TodoAdapter
import com.survivalcoding.todolist.todo.view.model.Todo

class MainFragment(private var model: TodoData) : Fragment() {
    private val todoAdapter: TodoAdapter by lazy {
        TodoAdapter(
            deleteOnClick = { model.deleteTodo(it) },
            textOnClick = { textOnClick(it) },
            update = { updateUI() },
            isDoneUpdate = { model.updateTodo(it) },
        )
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!  // !!....;;

    private var orderMethod = MainActivity.ASCENDING
    private var sortingBase = MainActivity.SORT_BY_TITLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        requireActivity().title = "Todo?"   // ActionBar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            todoList.adapter = todoAdapter
            addTodoButton.setOnClickListener {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.fragment_container_view, AddFragment(model))
                    addToBackStack(null)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            MainActivity.ROTATION_RESTORE_KEY,
            model.todoList as ArrayList<Todo>
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val list = savedInstanceState?.getParcelableArrayList<Todo>(MainActivity.ROTATION_RESTORE_KEY)
        list?.let { model.updateTodo(it.toList()) }
        updateUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_option, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sortToDDay -> {
                sortingBase = MainActivity.SORT_BY_D_DAY
                model.sorting(sortingBase = MainActivity.SORT_BY_D_DAY, order = orderMethod)
                updateUI()
                return true
            }
            R.id.sortToTitle -> {
                sortingBase = MainActivity.SORT_BY_TITLE
                model.sorting(sortingBase = MainActivity.SORT_BY_TITLE, order = orderMethod)
                updateUI()
                return true
            }
            R.id.ascending -> {
                item.isChecked = true
                orderMethod = MainActivity.ASCENDING
                model.sorting(sortingBase, order = MainActivity.ASCENDING)
                updateUI()
                return true
            }
            R.id.descending -> {
                item.isChecked = true
                orderMethod = MainActivity.DESCENDING
                model.sorting(sortingBase, order = MainActivity.DESCENDING)
                updateUI()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI() {
        todoAdapter.submitList(model.todoList.toList())
    }

    private fun textOnClick(item: Todo) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, EditFragment(model))
            addToBackStack(null)
        }
    }
}
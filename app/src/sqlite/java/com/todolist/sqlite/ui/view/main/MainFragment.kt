package com.todolist.sqlite.ui.view.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.todolist.sqlite.extension.replaceFragment
import com.todolist.sqlite.extension.replaceFragmentWithBundle
import com.todolist.sqlite.ui.adapter.TodoAdapter
import com.todolist.sqlite.ui.view.add.AddTodoFragment
import com.todolist.sqlite.ui.viewmodel.MainViewModel
import com.todolist.sqlite.util.TODO_ITEM
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    lateinit var todoAdapter: TodoAdapter
    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        eventProcess()
        setRecyclerView()
        setToolbar()
    }

    private fun getData() {
        viewModel.getTodoList()
    }

    private fun eventProcess() {
        binding.btnAddMain.setOnClickListener {
            replaceFragment<AddTodoFragment>(R.id.fragment_container)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setRecyclerView() {
        todoAdapter = TodoAdapter().apply {
            setTodoList(viewModel.todoList)
            setModifyTodoItemListener {
                replaceFragmentWithBundle(
                    R.id.fragment_container,
                    AddTodoFragment::class,
                    bundleOf(TODO_ITEM to it)
                )
            }

            setCompleteTodoItemListener {
                viewModel.updateTodoItem(it)
                viewModel.getTodoList()
                todoAdapter.setTodoList(viewModel.todoList)
            }

            setRemoveTodoItemListener {
                viewModel.removeTodoItem(it)
            }
        }


        binding.rvTodolistMain.apply {
            adapter = todoAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        hideKeyboard()
                        false
                    }
                    else -> false
                }
            }
        }

    }

    private fun setSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                todoAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                todoAdapter.filter.filter(query)
                return true
            }
        })
    }

    private fun setToolbar() {
        setHasOptionsMenu(true)
    }

    private fun hideKeyboard() {
        searchView.clearFocus()
        val im = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(searchView.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        setSearchView()
    }
}
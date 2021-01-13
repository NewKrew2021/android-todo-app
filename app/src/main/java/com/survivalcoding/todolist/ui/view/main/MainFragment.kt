package com.survivalcoding.todolist.ui.view.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.ui.adapter.TodoAdapter
import com.survivalcoding.todolist.ui.viewmodel.MainViewModel
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

    }

    private fun eventProcess() {
        binding.btnAddMain.setOnClickListener {
            parentFragmentManager.commit {

            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setRecyclerView() {
        todoAdapter = TodoAdapter().apply {
            setTodoList(viewModel.todoList)
            setModifyTodoItemListener {
                parentFragmentManager.commit {

                }
            }

            setSortTodoItemListener {
                viewModel.sortTodoItem()
                todoAdapter.setTodoList(viewModel.todoList)
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
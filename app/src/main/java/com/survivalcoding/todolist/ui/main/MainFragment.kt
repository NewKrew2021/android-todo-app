package com.survivalcoding.todolist.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.data.database.TodoSqliteRepository
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.utils.NavigationUtil

class MainFragment(private val todoRepository: DefaultTodoRepository) : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TodoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchButton.setOnClickListener {
            updateUi()
        }

        adapter = TodoListAdapter(
            updateUi = { updateUi() },
            updateItem = { todoItem -> todoRepository.updateItem(todoItem) },
            openTodoItem = { todoItem ->
                NavigationUtil.openTodoFragment(
                    parentFragmentManager,
                    todoItem
                )
            }
        )

        binding.list.apply {
            adapter = this@MainFragment.adapter
            addItemDecoration(VerticalSpaceItemDecoration(ITEM_VERTICAL_INTERVAL))
            itemAnimator = DefaultItemAnimator().apply {
                changeDuration = 100
                moveDuration = 100
            }
        }

        setHasOptionsMenu(true)
        updateUi()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.menu_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_button -> {
                NavigationUtil.openEditFragment(parentFragmentManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi() {

        val keyword = binding.input.text.toString()

        if (keyword.isNotBlank()) {
            adapter.submitList(todoRepository.getFilteredItemsBy(keyword))
        } else {
            adapter.submitList(todoRepository.getOrderedItems())
        }
    }

    companion object {
        private const val ITEM_VERTICAL_INTERVAL = 12
    }
}
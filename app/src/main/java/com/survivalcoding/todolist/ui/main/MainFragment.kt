package com.survivalcoding.todolist.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.repository.TodoRepository
import com.survivalcoding.todolist.utils.NavigationUtil

class MainFragment(private val todoRepository: TodoRepository) : Fragment() {

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

        binding.search.setOnClickListener {
            updateUi(todoRepository.getFilteredItems(binding.input.text.toString()))
        }

        adapter = TodoListAdapter(
            sort = { todoRepository.sortItems() },
            remove = { targetItem -> todoRepository.removeItem(targetItem) },
            update = { updateUi() }
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
                NavigationUtil.openAddFragment(parentFragmentManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList(MainActivity.SAVED_LIST_KEY, ArrayList(todoRepository.list))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.getParcelableArrayList<TodoItem>(MainActivity.SAVED_LIST_KEY)?.let {
            todoRepository.resetItems(it)
        }
        updateUi()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi() {

        if (binding.input.text.toString().isNotBlank()) {
            updateUi(todoRepository.getFilteredItems(binding.input.text.toString()))
        } else {
            adapter.submitList(todoRepository.list.toList())
        }
    }

    private fun updateUi(list: List<TodoItem>) {
        adapter.submitList(list)
    }

    companion object {
        private const val ITEM_VERTICAL_INTERVAL = 12
    }
}
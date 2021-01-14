package com.survivalcoding.todolist.view.main.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.extension.replaceTransaction
import com.survivalcoding.todolist.view.main.MainActivity
import com.survivalcoding.todolist.view.main.adapter.TodoAdapter
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.*

class MainFragment(
    private val repository: DefaultTodoRepository
) : Fragment(), RemoveConfirmationDialogFragment.RemoveConfirmationDialogListener {
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private var actionMode: ActionMode? = null

    private val adapter by lazy {
        TodoAdapter(
            showToastMessageListener = { message: String -> showToastMessage(message) },
            removeClickListener = { todo -> repository.remove(todo) },
            itemUpdateListener = { todo -> repository.update(todo) },
            updateUIListener = { updateUI() },
            editClickListener = { todo ->
                replaceTransaction<EditFragment>(
                    R.id.fragment_container_view,
                    bundleOf(MainActivity.TODO_KEY to todo)
                )
            },
            getActionMode = { getActionMode() },
            setActionBarTitle = { setActionBarTitle() },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) // onCreateOptionsMenu will Call

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )

            buttonAdd.setOnClickListener {
                if (editTextTitle.text.trim().isNotEmpty()) {
                    repository.add(
                        Todo(
                            editTextTitle.text.toString(),
                            Calendar.getInstance().timeInMillis
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
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { updateUI() }
                    return true
                }
            })
        }
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_mode_remove -> {
                actionMode = activity?.startActionMode(removeModeCallback)
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(IS_IN_ACTION_MODE_KEY, actionMode != null)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.let {
            onActionModeStateRestored(savedInstanceState)
        }
    }

    private fun onActionModeStateRestored(savedInstanceState: Bundle) {
        if (savedInstanceState.getBoolean(IS_IN_ACTION_MODE_KEY)) {
            actionMode = activity?.startActionMode(removeModeCallback)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateUI() {
        val query = binding.searchView.query.toString()

        adapter.submitList(
            if (query.isEmpty()) repository.getOrderedItems()
            else repository.getOrderedWithFilteredItems(query)
        )
    }

    private fun getActionMode(): ActionMode? = actionMode

    private fun setActionBarTitle() {
        actionMode?.title = getString(
            R.string.fragment_main_action_bar_mode_remove_title,
            repository.getRemovablesCount()
        )
    }

    // RemoveConfirmationDialog Listener
    override fun removeAllRemovables() {
        repository.removeAllRemovable()
        updateUI()
        actionMode?.finish()
    }

    override fun getRemovablesItemCount(): Int = repository.getRemovablesCount()

    private val removeModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.main_menu_remove, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.title = getString(
                R.string.fragment_main_action_bar_mode_remove_title,
                repository.getRemovablesCount()
            )
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.menu_remove -> {
                    val itemCount = repository.getRemovablesCount()
                    if (itemCount > 0) {
                        RemoveConfirmationDialogFragment()
                            .show(childFragmentManager, RemoveConfirmationDialogFragment.TAG)
                    }
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            adapter.notifyDataSetChanged()
        }
    }
    companion object {
        const val IS_IN_ACTION_MODE_KEY = "IS_IN_ACTION_MODE_KEY"
    }
}

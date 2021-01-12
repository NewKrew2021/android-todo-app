package com.survivalcoding.todolist.view.main.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.TodoViewModel
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.util.dateToString
import com.survivalcoding.todolist.view.main.MainActivity
import com.survivalcoding.todolist.view.main.adapter.TodoAdapter
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.*

class MainFragment(private val viewModel: TodoViewModel) : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var adapter: TodoAdapter

    private var actionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) // onCreateOptionsMenu will Call

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TodoAdapter(
            showToastMessageListener = { message: String -> showToastMessage(message) },
            removeClickListener = { todo -> viewModel.remove(todo) },
            updateListener = { updateUI() },
            editClickListener = { todo ->
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.fragment_container_view, EditFragment::class.java, bundleOf(MainActivity.TODO_KEY to todo))
                    addToBackStack(null)
                }
            },
            getActionMode = { getActionMode() },
            setActionBarTitle = { setActionBarTitle() },
        )

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

        outState.putInt(MainActivity.TODO_ID_KEY, viewModel.id.get())
        outState.putParcelableArrayList(MainActivity.TODO_STATE_KEY, viewModel.items as ArrayList<out Todo>)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.let {
            viewModel.id.set(savedInstanceState.getInt(MainActivity.TODO_ID_KEY))
            savedInstanceState.getParcelableArrayList<Todo>(MainActivity.TODO_STATE_KEY)?.let { items ->
                viewModel.addAll(items)
                updateUI()
            }
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}

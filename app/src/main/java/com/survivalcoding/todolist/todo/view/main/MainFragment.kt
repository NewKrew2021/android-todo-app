package com.survivalcoding.todolist.todo.view.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.todo.data.DefaultTodoData
import com.survivalcoding.todolist.todo.view.MainActivity
import com.survivalcoding.todolist.todo.view.OrderMethod
import com.survivalcoding.todolist.todo.view.SortingBase
import com.survivalcoding.todolist.todo.view.add.AddFragment
import com.survivalcoding.todolist.todo.view.edit.EditFragment
import com.survivalcoding.todolist.todo.view.main.adapter.TodoAdapter
import com.survivalcoding.todolist.todo.view.model.Todo

class MainFragment(private var model: DefaultTodoData) : Fragment() {
    private val todoAdapter: TodoAdapter by lazy {
        TodoAdapter(
            deleteOnClick = { model.deleteTodo(it) },
            textOnClick = { textOnClick(it) },
            update = { updateUI() },
            isDoneUpdate = { model.updateTodo(it) },
        )
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var sortingBase = SortingBase.SORT_BY_DATE
    private var orderMethod = OrderMethod.ASCENDING

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
        requireActivity().title = getString(R.string.app_name)   // ActionBar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            todoList.adapter = todoAdapter
            addTodoButton.setOnClickListener {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<AddFragment>(R.id.fragment_container_view)
                    addToBackStack(null)
                }
            }
        }
        updateUI()  // 이거덕분에 자동정렬되고있다.
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putParcelableArrayList(
//            MainActivity.BUNDLE_KEY,
//            model.todoList as ArrayList<Todo>
//        )
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        val list =
//            savedInstanceState?.getParcelableArrayList<Todo>(MainActivity.ROTATION_RESTORE_KEY)
//        list?.let { model.todoList = it }
//        updateUI()
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_option, menu)

        val searchView = menu?.findItem(R.id.search_button)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Do nothing
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
                return true
            }

        })
        MenuCompat.setGroupDividerEnabled(menu, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sortByWriteDate -> {
                sortingBase = SortingBase.SORT_BY_DATE
                updateUI()
                return true
            }
            R.id.sortToDDay -> {
                sortingBase = SortingBase.SORT_BY_D_DAY
                updateUI()
                return true
            }
            R.id.sortToTitle -> {
                sortingBase = SortingBase.SORT_BY_TITLE
                updateUI()
                return true
            }
            R.id.ascending -> {
                item.isChecked = true
                orderMethod = OrderMethod.ASCENDING
                updateUI()
                return true
            }
            R.id.descending -> {
                item.isChecked = true
                orderMethod = OrderMethod.DESCENDING
                updateUI()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI() {
        model.sorting(sortingBase, orderMethod, { todoAdapter.submitList(it) })
    }

    private fun textOnClick(item: Todo) {
        parentFragmentManager.commit {
            val data = bundleOf(MainActivity.BUNDLE_KEY to item)

            setReorderingAllowed(true)
            // 왜 아래처럼 안되나 했는데 import를 안했네...
            replace<EditFragment>(R.id.fragment_container_view, args = data)
//            replace(R.id.fragment_container_view, EditFragment(model).javaClass, data)
            addToBackStack(null)
        }
    }
}
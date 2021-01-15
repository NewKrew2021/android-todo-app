package com.survivalcoding.todolist.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.data.db.TodoSqliteRepository
import com.survivalcoding.todolist.databinding.FragmentListBinding
import com.survivalcoding.todolist.viewModel.listItem
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment(private val todoRepository: DefaultTodoRepository) : Fragment() {

    private lateinit var binding: FragmentListBinding

    private lateinit var adapter: RecyclerAdapter
    var imm: InputMethodManager? = null

    //lateinit var todoRepository: TodoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentListBinding.inflate(layoutInflater)
        val view = binding.root

        adapter =
            RecyclerAdapter(todoRepository) { adapter: RecyclerAdapter, position: Int ->

                EditDialogFragment({ dialogView: View ->
                    dialogSetting(
                        dialogView,
                        adapter,
                        position
                    )
                }).show(
                    childFragmentManager, EditDialogFragment.TAG
                )

            }
        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)

        if (todoRepository is TodoSqliteRepository) {
            todoRepository.readDatabase()
        }

        adapter.getData(

            todoRepository.getDataList(),
            todoRepository.getSearchDataList()

        )

        adapter.searching("")

        binding.addButton.setOnClickListener {
            addButtonListener(adapter, binding)
        }

        /*
        binding.editText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) addButtonListener(
                    adapter,
                    binding
                )
                else if (keyCode == KeyEvent.KEYCODE_BACK)
            }
            true
        }

         */
        binding.removeButton.setOnClickListener {
            adapter.checkedRemove(binding.searchEditText.text.toString())
        }

        binding.completeButton.setOnClickListener {

            adapter.checkedComplete(binding.searchEditText.text.toString())
        }
        binding.searchEditText.addTextChangedListener {
            adapter.searching(binding.searchEditText.text.toString())
        }

        return view
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main, menu)
    }


    fun hideKeyboard(v: View) {
        imm =
            activity?.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (v != null) {
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun dialogSetting(dialogView: View, adapter: RecyclerAdapter, position: Int) {
        val reviseText = dialogView.findViewById<EditText>(R.id.reviseText)

        var tmpString = reviseText.text.toString()
        val sdf = SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
        val date = System.currentTimeMillis()
        val currentDate = sdf.format(date)

        adapter.data.add(
            0,
            listItem(
                tmpString,
                currentDate,
                check = false,
                complete = false,
                id = adapter.searchData[position].item.id
            )
        )
        if (todoRepository is TodoSqliteRepository) {
            todoRepository.updateItem(
                listItem(
                    tmpString,
                    currentDate,
                    check = false,
                    complete = false,
                    id = adapter.searchData[position].item.id
                )
            )
        }

        adapter.data.removeAt(adapter.searchData[position].index + 1)

        //adapter.notifyItemRemoved(position+1)

        //adapter.notifyItemRangeChanged(0, adapter.data.size)
        adapter.notifyItemRemoved(position)
        adapter.makeSearchData(binding.searchEditText.text.toString())
        adapter.notifyItemRangeChanged(0, adapter.searchData.size)
    }

    fun addButtonListener(adapter: RecyclerAdapter, binding: FragmentListBinding) {

        adapter.addItem(binding.editText.text.toString())

        if (
            binding.editText.text.toString().contains(binding.searchEditText.text.toString())
        ) {
            adapter.notifyItemInserted(0)
        }
        adapter.notifyItemRangeChanged(0, adapter.data.size)

        binding.editText.setText("")
        adapter.makeSearchData(binding.searchEditText.text.toString())
        //adapter.dataUpdate()
        hideKeyboard(binding.root)

    }


}
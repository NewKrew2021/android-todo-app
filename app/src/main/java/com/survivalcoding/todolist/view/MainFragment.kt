package com.survivalcoding.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.extension.afterTextChanged
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.repository.DefaultTodoRepository
import com.survivalcoding.todolist.repository.database.MyCallback
import java.util.*


class MainFragment(private val repository: DefaultTodoRepository) : Fragment() {
    private var _binding: FragmentMainBinding? = null
    lateinit var todoListAdapter: TodoListAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoListAdapter = TodoListAdapter(
                _completeListener = {
                    it.isComplete = !it.isComplete
                    repository.update(it)
                    updateList()
                },
                _modifyListener = {
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragmentContainerView, MakeTodoFragment(repository, it))
                        addToBackStack(null)
                    }
                },
                _deleteListener = {
                    repository.remove(it)
                    updateList()
                },
                _markListener = {
                    it.isMark = !it.isMark
                    repository.update(it)
                    updateList()
                })
        binding.apply {
            todoListView.adapter = todoListAdapter
            mainLayout.setOnClickListener {
                val inputMethodManager =
                        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(searchEdit.windowToken, 0)
            }
            addButton.setOnClickListener {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.fragmentContainerView, MakeTodoFragment(repository))
                    addToBackStack(null)
                }
            }
            searchEdit.afterTextChanged {
                searchList(it)
            }
        }
        updateList()


    }




    private fun updateList() {
        repository.getOrderedItems(object : MyCallback{
            override fun getListCallBack(list: List<TodoItem>) {
                todoListAdapter.submitList(list)
            }
        })
    }


    private fun searchList(text : String){
        repository.getOrderedItems(object : MyCallback{
            override fun getListCallBack(list: List<TodoItem>) {
                val searchList = list.filter {
                    it.title.contains(text.toLowerCase(Locale.ROOT))
                }


                todoListAdapter.submitList(searchList.sortedWith(
                        compareBy<TodoItem>
                        { it.isComplete }.thenByDescending
                        { it.isMark }.thenBy
                        { it.date }
                ))
            }
        })
    }

}




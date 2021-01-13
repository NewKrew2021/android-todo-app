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
import com.survivalcoding.todolist.repository.DefaultTodoRepository


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
            searchEdit.afterTextChanged { text ->
                val searchList = repository.getOrderedItems().filter {
                    it.title.contains(text)
                }
                searchList.sortedWith(
                        compareBy(
                                { it.isComplete },
                                { it.isMark },
                                { it.date })
                )
                todoListAdapter.submitList(searchList)
            }
        }
        updateList()
    }


    private fun updateList() {
        todoListAdapter.submitList(repository.getOrderedItems())
    }

}




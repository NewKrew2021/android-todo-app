package com.survivalcoding.todolist.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.adapter.TodoListAdapter
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.viewmodel.SharedViewModel
import com.survivalcoding.todolist.viewmodel.TodoViewModel


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    lateinit var todoListAdapter: TodoListAdapter
    val todoViewModel: TodoViewModel = TodoViewModel()
    lateinit var sharedViewModel: SharedViewModel
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
                    todoViewModel.complete(it)
                    updateList()
                },
                _modifyListener = {
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragmentContainerView, MakeTodoFragment(FRAGMENT_EDIT_MODE, it))
                        addToBackStack(null)
                    }
                },
                _deleteListener = {
                    todoViewModel.remove(it)
                    updateList()
                },
                _markListener = {
                    todoViewModel.mark(it)
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
                    replace(R.id.fragmentContainerView, MakeTodoFragment(FRAGMENT_ADD_MODE))
                    addToBackStack(null)
                }
            }
            searchEdit.addTextChangedListener(getTextWatcher())
        }
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.getLiveData().observe(
                viewLifecycleOwner,
                object : Observer<TodoItem?> {
                    override fun onChanged(item: TodoItem?) {
                        if (item == null) {
                            Toast.makeText(
                                    requireActivity(),
                                    "취소 되었습니다.",
                                    Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            dataProcess(item)
                        }
                    }

                })
    }

    fun dataProcess(item: TodoItem) {
        val result = todoViewModel.getItemList().filter { it.id == item.id }.size
        if (result == 0) {// add
            item.id = dataId.id
            dataId.id += 1
            todoViewModel.add(item)
        } else {
            todoViewModel.modify(item)
        }
        updateList()
    }

    fun getTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val searchList = todoViewModel.getItemList().filter {
                    it.title.contains(s.toString())
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
    }

    companion object {
        const val FRAGMENT_ADD_MODE = "add"
        const val FRAGMENT_EDIT_MODE = "edit"
        var id: Int = 0
    }

    private fun updateList() {
        if (binding.searchEdit.text.isEmpty()) {
            todoListAdapter.submitList(todoViewModel.sort())
        } else { // 검색이 되어 있는 결과로
            todoListAdapter.submitList(
                    todoListAdapter.currentList.sortedWith(
                            compareBy(
                                    { it.isComplete },
                                    { it.isMark },
                                    { it.date })
                    )
            )
        }
    }
}

object dataId {
    var id: Int = 0
}


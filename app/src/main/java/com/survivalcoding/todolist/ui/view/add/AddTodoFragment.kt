package com.survivalcoding.todolist.ui.view.add

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.model.TodoItem
import com.survivalcoding.todolist.databinding.FragmentAddTodoBinding
import com.survivalcoding.todolist.ui.view.main.MainFragment
import com.survivalcoding.todolist.ui.viewmodel.AddTodoViewModel
import com.survivalcoding.todolist.util.TODO_ITEM
import com.survivalcoding.todolist.util.TODO_LIST
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AddTodoFragment : Fragment(R.layout.fragment_add_todo) {

    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddTodoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventProcess()
        isModifyTodoItem()
    }

    private fun isModifyTodoItem() {
        val contents = arguments?.getParcelable<TodoItem>(TODO_ITEM)?.contents
        if (contents != null) {
            binding.edtContentsAddTodo.text = Editable.Factory.getInstance().newEditable(contents)
        }
    }

    private fun eventProcess() {
        binding.btnSaveAddTodo.setOnClickListener {
            val result = arguments?.getParcelableArrayList<TodoItem>(TODO_LIST)
            result?.add(
                TodoItem(
                    time = Calendar.getInstance().timeInMillis,
                    contents = binding.edtContentsAddTodo.text.toString(),
                    complete = false
                )
            )
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(
                    R.id.fragment_container,
                    MainFragment::class.java,
                    bundleOf(TODO_LIST to result)
                )
            }

        }

        binding.btnCancelAddTodo.setOnClickListener {
            val result = arguments?.getParcelableArrayList<TodoItem>(TODO_LIST)
            if(arguments?.getParcelable<TodoItem>(TODO_ITEM) != null) {
                result?.add(
                    arguments?.getParcelable(TODO_ITEM)
                )
            }
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace(
                    R.id.fragment_container,
                    MainFragment::class.java,
                    bundleOf(TODO_LIST to result)
                )
            }
        }
    }
}
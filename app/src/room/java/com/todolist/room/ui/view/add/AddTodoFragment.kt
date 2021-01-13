package com.todolist.room.ui.view.add

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.FragmentAddTodoBinding
import com.todolist.room.data.model.TodoItem
import com.todolist.room.extension.replaceFragment
import com.todolist.room.ui.view.main.MainFragment
import com.todolist.room.ui.viewmodel.MainViewModel
import com.todolist.room.util.TODO_ITEM
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AddTodoFragment : Fragment() {

    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

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
            if (arguments == null) {
                viewModel.addTodoItem(
                    TodoItem(
                        time = Calendar.getInstance().timeInMillis,
                        contents = binding.edtContentsAddTodo.text.toString(),
                    )
                )
            } else {
                val todoItem = requireArguments().getParcelable<TodoItem>(TODO_ITEM)
                if (todoItem != null) {
                    viewModel.removeTodoItem(todoItem)
                    viewModel.addTodoItem(
                        TodoItem(
                            time = Calendar.getInstance().timeInMillis,
                            contents = binding.edtContentsAddTodo.text.toString(),
                        )
                    )
                }
            }
            replaceFragment<MainFragment>(R.id.fragment_container)
        }

        binding.btnCancelAddTodo.setOnClickListener {
            replaceFragment<MainFragment>(R.id.fragment_container)
        }
    }
}
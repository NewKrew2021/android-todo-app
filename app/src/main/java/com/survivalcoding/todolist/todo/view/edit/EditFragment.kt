package com.survivalcoding.todolist.todo.view.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.FragmentAddBinding
import com.survivalcoding.todolist.todo.data.DefaultTodoData
import com.survivalcoding.todolist.todo.view.MainActivity
import com.survivalcoding.todolist.todo.view.dialog.AlertDialogFragment
import com.survivalcoding.todolist.todo.view.model.Todo
import java.text.SimpleDateFormat
import java.util.*

class EditFragment(private val model: DefaultTodoData) : Fragment() {
    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        requireActivity().title = getString(R.string.edit_todo_actionbar_title)
        binding.addTodo.text = getString(R.string.edit_todo_confirm_button_title)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dueDate = Date().time
        val format = SimpleDateFormat(MainActivity.DATE_FORMAT)
        var itemId = 0

        arguments?.getParcelable<Todo>(MainActivity.BUNDLE_KEY)?.let {
            binding.todoEditText.setText(it.text)
            binding.dueDate.date = it.dueDate
            itemId = it.id
        }

        binding.cancelButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.dueDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val dateString = "$year-${month + 1}-$dayOfMonth"
            dueDate = format.parse(dateString).time
        }
        binding.addTodo.setOnClickListener {
            val todoText = binding.todoEditText.text.toString()
            if (todoText.trim().isEmpty()) {
                AlertDialogFragment(getString(R.string.alert_text)).show(
                    parentFragmentManager,
                    AlertDialogFragment.TAG
                )
            } else {
                val today = Date().time
                // addTodo가 아닌, updateTodo로 변경
                model.updateTodo(Todo(false, todoText, dueDate, today, itemId))
                parentFragmentManager.popBackStack()
            }
        }
    }
}
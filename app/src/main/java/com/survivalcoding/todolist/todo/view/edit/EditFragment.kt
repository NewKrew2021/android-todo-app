package com.survivalcoding.todolist.todo.view.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.databinding.FragmentAddBinding
import com.survivalcoding.todolist.todo.data.TodoData
import com.survivalcoding.todolist.todo.view.MainActivity
import com.survivalcoding.todolist.todo.view.dialog.AlertDialogFragment
import com.survivalcoding.todolist.todo.view.model.Todo
import java.text.SimpleDateFormat
import java.util.*

class EditFragment(private val model: TodoData) : Fragment() {
    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
//        requireActivity().title = "Modify Todo"
        binding.addTodo.text = "수정"
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
                AlertDialogFragment("할 일을 입력해 주세요").show(
                    parentFragmentManager,
                    AlertDialogFragment.TAG
                )
            } else {
                model.addTodo(Todo(false, todoText, dueDate))
                parentFragmentManager.popBackStack()
            }
        }
    }
}
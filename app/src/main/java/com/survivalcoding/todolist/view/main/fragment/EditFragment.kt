package com.survivalcoding.todolist.view.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.databinding.FragmentEditBinding
import com.survivalcoding.todolist.extension.finish
import com.survivalcoding.todolist.view.main.MainActivity
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.*

class EditFragment(private val repository: DefaultTodoRepository) : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todo = arguments?.getParcelable<Todo>(MainActivity.TODO_KEY)

        with(binding) {
            buttonOk.setOnClickListener {
                if (editTextTitle.text.isNotBlank()) {
                    if (todo == null) {
                        showToastMessage(getString(R.string.fragment_edit_todo_edit_error_text))
                    } else {
                        repository.update(todo.apply {
                            title = editTextTitle.text.toString()
                            times = Calendar.getInstance().timeInMillis
                        })
                        finish()
                    }
                }
            }
            buttonCancel.setOnClickListener { finish() }
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
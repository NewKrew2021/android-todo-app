package com.survivalcoding.todolist.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.databinding.FragmentEditBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.utils.NavigationUtil
import java.util.*

class EditFragment(private val todoRepository: DefaultTodoRepository) : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private var todoItem: TodoItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<TodoItem>("picked")?.let {

            binding.apply {
                title.setText(it.title)
                content.setText(it.content)
            }
            todoItem = it
        }

        binding.addButton.setOnClickListener {

            if (todoItem != null) editTodo()
            else addTodo()
        }
    }

    private fun addTodo() {

        val title = binding.title.text.toString()
        val timeStamp = Calendar.getInstance().timeInMillis
        val content = binding.content.text.toString()

        if (title.isNotBlank()) {
            todoRepository.addItem(TodoItem(title, false, timeStamp, content))
            NavigationUtil.openMainFragment(parentFragmentManager)
        }
    }

    private fun editTodo() {

        todoItem?.let {
            todoRepository.updateItem(it.apply {
                title = binding.title.text.toString()
                timeStamp = Calendar.getInstance().timeInMillis
                content = binding.content.text.toString()
            })
        }
        NavigationUtil.openMainFragment(parentFragmentManager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
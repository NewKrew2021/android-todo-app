package com.survivalcoding.todolist.view.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.databinding.FragmentAddBinding
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.*

class AddFragment(private val repository: DefaultTodoRepository) : Fragment() {
    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        requireActivity().title = "할일 작성"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            if (binding.todoText.text.isNotEmpty()) {
                val todo = binding.todoText.text.toString()
                repository.addTodo(Todo(todo, Date().time))

                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
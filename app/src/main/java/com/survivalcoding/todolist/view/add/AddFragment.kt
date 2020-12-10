package com.survivalcoding.todolist.view.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.data.TodoRepository
import com.survivalcoding.todolist.databinding.FragmentAddBinding
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.*

class AddFragment(private val repository: TodoRepository) : Fragment() {
    interface OnTodoAddListener {
        fun onTodoAdded()
    }

    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    private var todoAddListener: OnTodoAddListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        todoAddListener = context as OnTodoAddListener
    }

    override fun onDetach() {
        todoAddListener = null
        super.onDetach()
    }

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
                todoAddListener?.onTodoAdded()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
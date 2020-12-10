package com.survivalcoding.todolist.view.edit

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.TodoRepository
import com.survivalcoding.todolist.databinding.FragmentAddBinding
import com.survivalcoding.todolist.view.main.MainActivity
import com.survivalcoding.todolist.view.main.model.Todo
import java.util.*

class EditFragment(private val repository: TodoRepository) : Fragment() {
    private var _binding: FragmentAddBinding? = null

    private val binding get() = _binding!!

    lateinit var todo: Todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.addButton.text = "수정"
        requireActivity().title = "할일 수정"

        arguments?.getParcelable<Todo>(MainActivity.TODO)?.let {
            todo = it
            binding.todoText.setText(it.todo)
        }

        binding.addButton.setOnClickListener {
            if (todo.todo != binding.todoText.text.toString()) {
                todo.todo = binding.todoText.text.toString()
                todo.datetime = Date().time

                repository.updateTodo(todo)

                finish()
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                repository.removeTodo(todo)

                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun finish() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
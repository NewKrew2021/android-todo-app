package com.survivalcoding.todolist.ui.todo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.databinding.FragmentTodoBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.utils.NavigationUtil

class TodoFragment(private val todoRepository: DefaultTodoRepository) : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private var todoItem: TodoItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<TodoItem>("picked")?.let {

            binding.apply {
                title.text = it.title
            }
            todoItem = it
        }

        binding.editButton.setOnClickListener {
            NavigationUtil.openEditFragment(parentFragmentManager, todoItem)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.menu_todo_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.remove_button -> {
                todoItem?.let {
                    todoRepository.removeItem(it)
                    NavigationUtil.popThisFragment(parentFragmentManager)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
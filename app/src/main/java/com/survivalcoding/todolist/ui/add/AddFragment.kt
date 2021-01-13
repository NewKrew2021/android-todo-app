package com.survivalcoding.todolist.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.data.database.TodoSqliteRepository
import com.survivalcoding.todolist.databinding.FragmentAddBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.utils.NavigationUtil
import java.util.*

class AddFragment(private val todoRepository: TodoSqliteRepository) : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.add.setOnClickListener {

            if (binding.title.text.toString().isNotEmpty()) {
                addTodo()
            } else {
                // TODO 내용 입력 메세지 띄우기
            }
        }
    }

    private fun addTodo() {

        val title = binding.title.text.toString()
        val timeStamp = Calendar.getInstance().timeInMillis

        todoRepository.addItem(TodoItem(title, false, timeStamp))
        NavigationUtil.popThisFragment(parentFragmentManager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
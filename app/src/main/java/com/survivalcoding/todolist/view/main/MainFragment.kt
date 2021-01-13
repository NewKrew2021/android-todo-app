package com.survivalcoding.todolist.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.data.TodoLocalRepository
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.view.main.adapter.TodoRecyclerViewAdapter
import com.survivalcoding.todolist.view.main.model.TodoData
import java.util.*


class MainFragment(private val repository: TodoLocalRepository) : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        TodoRecyclerViewAdapter(repository,
            deleteClickListener = { todo ->
                repository.delItem(todo)
                updateUi()
            },
            editClickListener = { todo -> })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        requireActivity().title = "Todo List"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.buttonAdd.setOnClickListener {
            //To-Do 항목 추가
            if (binding.editTodo.text.isEmpty()) {
                Toast.makeText(view.context, ALERT_RENAME, Toast.LENGTH_SHORT).show()
            } else {
                repository.addItem(TodoData(binding.editTodo.text.toString()))
                binding.editTodo.text.clear()
                updateUi()

            }
        }
        updateUi()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            DATA_SAVE,
            repository.items as ArrayList<TodoData>
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            val savedData = savedInstanceState.getParcelableArrayList<TodoData>(DATA_SAVE)
            savedData?.let { repository.addAllItems(savedData.toList()) }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi() {
        adapter.submitList(repository.items.toList())
    }


    companion object {
        private const val DATA_SAVE = "todo"
        private const val ALERT_RENAME = "내용을 입력해주세요."
    }
}
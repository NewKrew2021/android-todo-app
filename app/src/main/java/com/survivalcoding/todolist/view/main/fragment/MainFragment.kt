package com.survivalcoding.todolist.view.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.data.TodoRepository
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.view.main.adapter.TodoRecyclerViewAdapter
import com.survivalcoding.todolist.view.main.model.TodoData


class MainFragment(private val repository: TodoRepository) : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        TodoRecyclerViewAdapter(repository,
            deleteClickListener = { todo ->
                RemoveDialogFragment().apply {
                    isCancelable = false
                    submitButtonClickListener = {
                        repository.delItem(todo)
                        updateUi()
                    }
                }.show(childFragmentManager, REMOVE_DIALOG_TAG)
            },
            editClickListener = { todo, changeTodo ->
                repository.editItem(todo, changeTodo)
                updateUi()
            },
            checkBoxClickListener = { todo ->
                repository.doneItem(todo)
                updateUi()
            })
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi() {
        adapter.submitList(repository.getItems())
        adapter.notifyDataSetChanged()
    }


    companion object {
        private const val DATA_SAVE = "todo"
        private const val ALERT_RENAME = "내용을 입력해주세요."
        private const val REMOVE_DIALOG_TAG = "edit"
    }
}
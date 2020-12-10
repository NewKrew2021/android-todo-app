package com.survivalcoding.todolist.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.TodoRepository
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.view.add.AddFragment
import com.survivalcoding.todolist.view.edit.EditFragment
import com.survivalcoding.todolist.view.main.adapter.TodoRecyclerAdapter
import com.survivalcoding.todolist.view.main.model.Todo

class MainFragment(private val repository: TodoRepository) : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private val todoAdapter by lazy {
        TodoRecyclerAdapter {
            val bundle = bundleOf(MainActivity.TODO to it)

            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<EditFragment>(R.id.fragment_container_view, args = bundle)
                addToBackStack(null)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        requireActivity().title = "할일 목록"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.memoRecyclerView) {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = todoAdapter
        }

        binding.addButton.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddFragment>(R.id.fragment_container_view)
                addToBackStack(null)
            }
        }

        updateUi()
    }

    private fun updateUi() {
        todoAdapter.submitList(repository.getOrderedItems())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(MainActivity.STATE_LAST_ID, repository.id.get())
        outState.putParcelableArrayList(MainActivity.STATE_LIST, ArrayList(repository.items))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState?.let {
            repository.id.set(savedInstanceState.getInt(MainActivity.STATE_LAST_ID))
            savedInstanceState.getParcelableArrayList<Todo>(MainActivity.STATE_LIST)?.let {
                repository.items = it
            }
            updateUi()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
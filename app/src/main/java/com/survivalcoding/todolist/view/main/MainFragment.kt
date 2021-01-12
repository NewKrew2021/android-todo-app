package com.survivalcoding.todolist.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.FragmentMainBinding
import com.survivalcoding.todolist.view.main.adapter.TodoRecyclerViewAdapter
import com.survivalcoding.todolist.view.main.model.TodoData
import java.util.*


class MainFragment : Fragment() {

    private fun itemClickListener(item: TodoData) {
        // todo click시 로직 변경 필요
    }

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!
    var pid = 0
    var isEdit = false

    private val todoRecyclerViewAdapter by lazy {
        TodoRecyclerViewAdapter(itemClickListener = ::itemClickListener)
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

        with(binding.recyclerView) {
            adapter = todoRecyclerViewAdapter
        }
        var isMarked = false
        binding.markBox.setOnClickListener {
            isMarked = !isMarked
            if (isMarked) binding.markBox.setImageResource(R.drawable.ic_baseline_star_24)
            else binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
        fun clickAction() {
            //To-Do 항목 추가
            if (binding.editTodo.text.isEmpty()) {
                Toast.makeText(view.context, ALERT_RENAME, Toast.LENGTH_SHORT).show()
            } else {
                todoRecyclerViewAdapter.addItem(
                    TodoData(
                        binding.editTodo.text.toString(),
                        Calendar.getInstance().timeInMillis,
                        isMarked = isMarked,
                        pid = pid++,
                    )
                )
                binding.editTodo.text.clear()
                binding.markBox.setImageResource(R.drawable.ic_baseline_star_outline_24)
                isMarked = false

            }
        }
        binding.buttonAdd.setOnClickListener {
            clickAction()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            DATA_SAVE,
            todoRecyclerViewAdapter.items as ArrayList<TodoData>
        )
        outState.putInt(PID_SAVE, pid)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            pid = savedInstanceState.getInt(PID_SAVE)
            val savedData = savedInstanceState.getParcelableArrayList<TodoData>(DATA_SAVE)
            savedData?.let { todoRecyclerViewAdapter.addAllItems(savedData.toList()) }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val DATA_SAVE = "todo"
        private const val PID_SAVE = "pid"
        private const val ALERT_RENAME = "내용을 입력해주세요."
    }
}
package com.survivalcoding.todolist.view

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.FragmentMakeTodoBinding
import com.survivalcoding.todolist.model.TodoItem
import com.survivalcoding.todolist.repository.DefaultTodoRepository
import java.text.SimpleDateFormat
import java.util.*

class MakeTodoFragment(private val repository: DefaultTodoRepository,private val oldItem: TodoItem? = null) : Fragment() {
    private var _binding: FragmentMakeTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var backPressCallback: OnBackPressedCallback
//    lateinit var sharedViewModel: SharedViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMakeTodoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(requireContext(), "취소 되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback)
    }

    override fun onDetach() {
        backPressCallback.remove()
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (oldItem != null) {
            binding.apply {
                titleEdit.setText(oldItem.title)
                val date = Date(oldItem.date);
                val calendar =  Calendar.getInstance()
                calendar.time = date
                dateView.text = String.format(
                        "%d-%02d-%02d",
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH).plus(1),
                        calendar.get(Calendar.DAY_OF_MONTH)
                )
                addTodoButton.text = "수정"
            }
            //modify mode 인 경우
        }

        binding.apply {
            dateView.setOnClickListener {
                datePickerShow(dateView)
            }
            addTodoButton.setOnClickListener {
                if (titleEdit.text.isEmpty()) {
                    Toast.makeText(requireContext(), "할일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    if (dateView.text.toString() == "yyyy - mm - dd") {
                        Toast.makeText(requireContext(), "기한을 선택해주세요", Toast.LENGTH_SHORT).show()
                    } else {
                        val date = SimpleDateFormat(
                                "yyyy-MM-dd",
                                Locale.KOREA
                        ).parse(dateView.text.toString())
                                ?: Date(Calendar.getInstance().timeInMillis)
                        lateinit var data: TodoItem
                        if (oldItem != null) {
                            data = TodoItem(
                                    title = titleEdit.text.toString(),
                                    date = date.time,
                                    isComplete = false,
                                    isMark = oldItem.isMark,
                                    id = oldItem.id
                            )
                            repository.update(data)

                        } else {
                            data = TodoItem(
                                    title = titleEdit.text.toString(),
                                    date = date.time,
                                    isComplete = false,
                                    isMark = false
                            )
                            repository.add(data)
                        }
                        finish()
                    }
                }
            }
        }

    }

    private fun datePickerShow(txtView: TextView) {
        val currentTime = Calendar.getInstance()
        val year = currentTime.get(Calendar.YEAR)
        val month = currentTime.get(Calendar.MONTH)
        val day = currentTime.get(Calendar.DAY_OF_MONTH)
        val datePicker = DatePickerDialog(
                requireContext(),
                R.style.SpinnerDatePickerStyle,
                object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        txtView.text = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
                    }
                },
                year,
                month,
                day + 1
        );

        datePicker.show()
    }

    private fun finish() {
        parentFragmentManager.popBackStack()
    }

}
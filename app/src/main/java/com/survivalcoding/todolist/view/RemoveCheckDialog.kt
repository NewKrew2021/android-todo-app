package com.survivalcoding.todolist.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.model.TodoItem

class RemoveCheckDialog(
    private val todoItem: TodoItem,
    private val positiveButtonClickListener: (TodoItem) -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.check_remove_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                positiveButtonClickListener(todoItem)
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .create()

    companion object {
        const val TAG = "RemoveCheckDialog"
    }
}
package com.survivalcoding.todolist.todo.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AlertDialogFragment(private val message: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("확인") { _, _ -> }
            .create()
    }

    companion object {
        const val TAG = "AlertDialogFragment"
    }
}
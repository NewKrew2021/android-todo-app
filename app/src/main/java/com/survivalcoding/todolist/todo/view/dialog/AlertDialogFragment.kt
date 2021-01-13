package com.survivalcoding.todolist.todo.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.survivalcoding.todolist.R

class AlertDialogFragment(private val message: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton(getString(R.string.alert_dialog_confirm)) { _, _ -> }
            .create()
    }

    companion object {
        const val TAG = R.string.alert_dialog_tag.toString()
    }
}
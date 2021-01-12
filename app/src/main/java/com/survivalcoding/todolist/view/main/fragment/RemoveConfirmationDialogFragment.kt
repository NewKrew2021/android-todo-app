package com.survivalcoding.todolist.view.main.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.survivalcoding.todolist.R

class RemoveConfirmationDialogFragment(
    private val itemCount: Int,
    private val removeListener: () -> Unit,
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setMessage(getString(R.string.dialog_remove_confirmation_message, itemCount))
            .setPositiveButton(getString(R.string.dialog_remove_confirmation_ok_text)) { _, _ -> removeListener.invoke() }
            .setNegativeButton(getString(R.string.dialog_remove_confirmation_cancel_text)) { _, _ -> dismiss() }
            .create()
    }

    companion object {
        const val TAG = "RemoveConfirmationDialog"
    }
}
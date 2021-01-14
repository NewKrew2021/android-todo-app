package com.survivalcoding.todolist.view.main.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.survivalcoding.todolist.R

class RemoveConfirmationDialogFragment : DialogFragment() {

    interface RemoveConfirmationDialogListener {
        fun removeAllRemovables()
        fun getRemovablesItemCount(): Int
    }
    private var listener: RemoveConfirmationDialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragment?.let {
            listener = (it as MainFragment)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setMessage(getString(R.string.dialog_remove_confirmation_message, listener?.getRemovablesItemCount() ?: 0))
            .setPositiveButton(getString(R.string.dialog_remove_confirmation_ok_text)) { _, _ -> listener?.removeAllRemovables() }
            .setNegativeButton(getString(R.string.dialog_remove_confirmation_cancel_text)) { _, _ -> dismiss() }
            .create()
    }

    companion object {
        val TAG: String = RemoveConfirmationDialogFragment::class.java.simpleName
    }
}
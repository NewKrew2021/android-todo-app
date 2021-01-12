package com.survivalcoding.todolist.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.survivalcoding.todolist.R
import java.lang.IllegalStateException

class EditDialogFragment(private val dialogSetting: (View) -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val viewDialog = inflater.inflate(R.layout.dialog1, null)
            AlertDialog.Builder(requireContext())
                .setView(viewDialog)
                .setMessage("test")
                .setPositiveButton("확인") { _, _ ->
                    dialogSetting(viewDialog)
                }
                .setNegativeButton("취소") { _, _ ->
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "EditDialog"
    }
}
package com.survivalcoding.todolist.view.main.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class RemoveDialogFragment : DialogFragment() {
    var submitButtonClickListener: () -> (Unit) = { -> }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage("삭제하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                submitButtonClickListener.invoke()
            }
            .setNegativeButton("취소") { _, _ ->

            }.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}
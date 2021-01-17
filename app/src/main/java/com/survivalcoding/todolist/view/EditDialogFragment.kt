package com.survivalcoding.todolist.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.DefaultTodoRepository
import com.survivalcoding.todolist.data.db.TodoSqliteRepository
import com.survivalcoding.todolist.viewModel.listItem
import java.lang.IllegalStateException
import java.text.SimpleDateFormat

class EditDialogFragment() : DialogFragment() {


    lateinit var dialogSetting: (View) -> Unit

    constructor(dialogSetting: (View) -> Unit) : this() {
        this.dialogSetting = dialogSetting
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        retainInstance = true
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater;
        val viewDialog = inflater.inflate(R.layout.dialog1, null)
        return AlertDialog.Builder(requireContext())
            .setView(viewDialog)
            .setMessage("test")
            .setPositiveButton("확인") { _, _ ->
                dialogSetting(viewDialog)
            }
            .setNegativeButton("취소") { _, _ ->

            }.create()
    }

    companion object {
        const val TAG = "EditDialog"
    }
}

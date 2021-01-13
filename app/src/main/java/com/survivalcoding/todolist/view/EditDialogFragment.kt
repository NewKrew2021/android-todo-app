package com.survivalcoding.todolist.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.db.TodoSqliteRepository
import com.survivalcoding.todolist.viewModel.listItem
import java.lang.IllegalStateException
import java.text.SimpleDateFormat

class EditDialogFragment() : DialogFragment() {

    lateinit var adapter: RecyclerAdapter
    var position: Int? = null
    lateinit var todoSqliteRepository: TodoSqliteRepository
    lateinit var textData: String
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val viewDialog = inflater.inflate(R.layout.dialog1, null)
            builder.setView(viewDialog)
                .setMessage("test")
                .setPositiveButton("확인") { _, _ ->
                    dialogSetting(viewDialog, adapter, position!!)
                }
                .setNegativeButton("취소") { _, _ ->
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun dialogSetting(dialogView: View, adapter: RecyclerAdapter, position: Int?) {
        val reviseText = dialogView.findViewById<EditText>(R.id.reviseText)
        if (position == null) return

        var tmpString = reviseText.text.toString()
        val sdf = SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
        val date = System.currentTimeMillis()
        val currentDate = sdf.format(date)

        todoSqliteRepository.data.add(
            0,
            listItem(
                tmpString,
                currentDate,
                check = false,
                complete = false,
            )
        )
        todoSqliteRepository.data.removeAt(todoSqliteRepository.searchData[position].index + 1)

        //adapter.notifyItemRemoved(position+1)

        //adapter.notifyItemRangeChanged(0, adapter.data.size)
        adapter.notifyItemRemoved(position)
        todoSqliteRepository.makeSearchData(textData)
        adapter.notifyItemRangeChanged(0, todoSqliteRepository.searchData.size)
    }

    companion object {
        const val TAG = "EditDialog"
    }
}
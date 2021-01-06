package com.survivalcoding.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.model.Todo

// TODO : isDone, Times 에 따른 정렬 구현

class TodoAdapter(
    private val context: Context,
    private val items: MutableList<Todo>,
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_todo_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.apply {
            btnEdit.setOnClickListener {
                // TODO : todo 제목 수정 구현
            }

            btnDelete.setOnClickListener {
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, items.size - position)

                Toast.makeText(context, "${textTitle.text} 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val layout: ConstraintLayout
        val textTitle: TextView
        private val textTimes: TextView
        private val checkBox: CheckBox
        private val btnMenus: ImageButton
        val btnEdit: ImageButton
        val btnDelete: ImageButton

        init {
            itemView.apply {
                layout = findViewById(R.id.layout_item)
                textTitle = findViewById(R.id.textView_title)
                textTimes = findViewById(R.id.textView_times)
                checkBox = findViewById(R.id.checkBox)
                btnMenus = findViewById(R.id.button_menus)
                btnEdit = findViewById(R.id.button_edit)
                btnDelete = findViewById(R.id.button_delete)
            }
        }

        fun bind(todo: Todo) {
            textTitle.text = todo.title
            textTimes.text = todo.times
            checkBox.isChecked = todo.isDone

            updateButtonsVisibility(todo.isOption)

            checkBox.setOnClickListener {
                todo.isDone = checkBox.isChecked
            }

            btnMenus.setOnClickListener {
                todo.isOption = true
                updateButtonsVisibility(todo.isOption)
            }

            layout.setOnClickListener {
                if (todo.isOption) {
                    todo.isOption = false
                    updateButtonsVisibility(todo.isOption)
                }
            }
        }

        private fun updateButtonsVisibility(isOption: Boolean) {
            btnMenus.visibility = if (isOption) View.INVISIBLE else View.VISIBLE
            btnEdit.visibility = if (isOption) View.VISIBLE else View.INVISIBLE
            btnDelete.visibility = if (isOption) View.VISIBLE else View.INVISIBLE
        }
    }
}
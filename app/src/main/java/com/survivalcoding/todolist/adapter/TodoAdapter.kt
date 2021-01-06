package com.survivalcoding.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.model.Todo

// TODO : isDone, Times 에 따른 정렬 구현

class TodoAdapter(
    private val context: Context,
    private val items: MutableList<Todo>,
) : BaseAdapter() {

    class TodoViewHolder {
        lateinit var layout: ConstraintLayout
        lateinit var textTitle: TextView
        lateinit var textTimes: TextView
        lateinit var checkBox: CheckBox
        lateinit var btnMenus: ImageButton
        lateinit var btnEdit: ImageButton
        lateinit var btnDelete: ImageButton

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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: TodoViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.item_todo_list, parent, false)

            holder = TodoViewHolder()
            holder.apply {
                layout = view.findViewById(R.id.layout_item)
                textTitle = view.findViewById(R.id.textView_title)
                textTimes = view.findViewById(R.id.textView_times)
                checkBox = view.findViewById(R.id.checkBox)
                btnMenus = view.findViewById(R.id.button_menus)
                btnEdit = view.findViewById(R.id.button_edit)
                btnDelete = view.findViewById(R.id.button_delete)
            }

            view.tag = holder
        } else {
            view = convertView
            holder = (view.tag) as TodoViewHolder
        }

        holder.bind(getItem(position))
        holder.apply {
            btnEdit.setOnClickListener {
                // TODO : todo 제목 수정 구현
            }

            btnDelete.setOnClickListener {
                items.removeAt(position)
                notifyDataSetChanged()

                Toast.makeText(context, "${textTitle.text} 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun getItem(position: Int): Todo = items[position]

    override fun getItemId(position: Int): Long = 0L

    override fun getCount(): Int = items.size
}
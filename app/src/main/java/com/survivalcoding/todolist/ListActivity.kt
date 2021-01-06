package com.survivalcoding.todolist

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.todolist.databinding.ActivityListBinding
import com.survivalcoding.todolist.databinding.Dialog1Binding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var bindingDialog: Dialog1Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var builder = AlertDialog.Builder(this)
        val adapter = MyAdapterRecycler() { adapter : MyAdapterRecycler, position:Int->
            //Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()
            val dialogView = layoutInflater.inflate(R.layout.dialog1, null)
            //builder = AlertDialog.Builder(this).setView(dialogView).setTitle("hello")

            builder.setView(dialogView).setTitle("수정사항을 입력하세요")
                .setPositiveButton("확인") { dialogInterface, i ->

                    var reviseText = dialogView.findViewById<EditText>(R.id.reviseText)
                   //bindingDialog.reviseText.text

                    var tmpString = reviseText.text.toString()
                    val sdf = java.text.SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")
                    val date = System.currentTimeMillis()
                    val currentDate = sdf.format(date)
                    adapter.data[position].toDo=tmpString
                    adapter.data[position].time=currentDate
                    //adapter.data[position]= listItem(tmpString,currentDate)
                   // adapter.data.add(0,listItem(tmpString,currentDate))

                    adapter.notifyItemChanged(position)

                }
                .setNegativeButton("취소") { dialogInterface, i ->

                }
                .show()


        }
        binding.RecyclerView.adapter = adapter
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        val sdf = java.text.SimpleDateFormat("yyyy/MM/dd - hh:mm:ss")


        binding.addButton.setOnClickListener {

            val date = System.currentTimeMillis()
            val currentDate = sdf.format(date)
            adapter.data.add(0, listItem(binding.editText.text.toString(), currentDate))
            adapter.notifyItemInserted(0)
            binding.editText.setText("")
        }

    }


}

package com.survivalcoding.todolist.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.data.db.TodoSqliteRepository
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.factory.MyFragmentFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager.fragmentFactory =
            MyFragmentFactory(TodoSqliteRepository(applicationContext))

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ListFragment>(R.id.fragment_container_view)
            }
        }

        /*
        binding.startButton.setOnClickListener {

            var intent = Intent(this, ListActivity::class.java)
            startActivityForResult(intent, 101)
        }

         */
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            101 -> {
                if (resultCode == Activity.RESULT_OK) {
                    var string = data!!.getStringExtra("key").toString()
                    var toast = Toast.makeText(this, string, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.LEFT, 400, 400)
                    toast.show()
                }
            }
        }
    }

     */
}


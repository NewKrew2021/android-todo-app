package com.survivalcoding.todolist.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, TodoFragment())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_app -> {
                startActivityForResult(
                    Intent(this, AppInfoActivity::class.java),
                    GO_TO_APP_INFO_ACTIVITY
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GO_TO_APP_INFO_ACTIVITY && resultCode == RESULT_OK) {
            Toast.makeText(
                this,
                data?.getStringExtra(AppInfoActivity.INFO_KEY),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val SAVE_INSTANCE_TODO_ITEM_KEY = "todoList"
        const val GO_TO_APP_INFO_ACTIVITY = 123
    }
}
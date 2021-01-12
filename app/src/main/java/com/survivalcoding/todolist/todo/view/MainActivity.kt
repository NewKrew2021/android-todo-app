package com.survivalcoding.todolist.todo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityMainBinding
import com.survivalcoding.todolist.todo.data.TodoData
import com.survivalcoding.todolist.todo.factory.MainFragmentFactory
import com.survivalcoding.todolist.todo.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var model = TodoData()


    override fun onCreate(savedInstanceState: Bundle?) {
        // fragmentFactory : 데이터를 bundle이 아닌, 생성자로 넘기기 위해 사용한다.
        supportFragmentManager.fragmentFactory = MainFragmentFactory(model)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val fragment = supportFragmentManager.fragmentFactory.instantiate(
                classLoader,
                MainFragment::class.java.name
            )

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, fragment, FRAGMENT_KEY)
            }
        }
    }

    companion object {
        const val ROTATION_RESTORE_KEY = "listData"
        const val DATE_FORMAT = "yyyy-mm-dd"
        const val FRAGMENT_KEY = "todo"
        const val ASCENDING = 123    // 1, 2이면 다른 값과 중복되지 않을까 해서 임의의 값을 넣었다.
        const val DESCENDING = 321
        const val SORT_BY_TITLE = 1000     // 제목순 정렬
        const val SORT_BY_D_DAY = 2000     // 남은 D-day순으로 정렬
        const val SORT_BY_DATE = 3000      // 등록한 날짜순으로 정렬
        const val ADD_REQUEST_QUEUE = 100
        const val EDIT_REQUEST_QUEUE = 200
        const val ONE_DAY_MILLISECONDS = 24 * 60 * 60 * 1000 // 86_400_000
    }
}
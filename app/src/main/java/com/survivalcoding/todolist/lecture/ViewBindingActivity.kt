package com.survivalcoding.todolist.lecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R
import com.survivalcoding.todolist.databinding.ActivityLectureBinding
import com.survivalcoding.todolist.databinding.ItemWeatherBinding

class ViewBinding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val data = listOf<Weather>(
            Weather("흐림", "판교", "-10"),
            Weather("흐림", "판교", "-10"),
            Weather("흐림", "판교", "-10"),
            Weather("흐림", "판교", "-10"),
            Weather("흐림", "판교", "-10"),
            Weather("흐림", "판교", "-10"),
            Weather("흐림", "판교", "-10"),
            Weather("흐림", "판교", "-10"),
            Weather("흐림", "판교", "-10"),
            Weather("흐림", "판교", "-10"),
        )


        val binding = ActivityLectureBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.listView.adapter = CustomAdapter2(data)
        binding.listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "$position 번째 클릭", Toast.LENGTH_SHORT).show()
        }
    }
}

class CustomAdapter2(private val data: List<Weather>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        lateinit var itemWeatherBinding: ItemWeatherBinding
        if (convertView == null) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_todo, parent, false)
            itemWeatherBinding =
                ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            view = convertView
            val currentWeather = getItem(position)

            itemWeatherBinding.cityText.text = currentWeather.city
            itemWeatherBinding.tempText.text = currentWeather.temp
        }

        return view
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size
}
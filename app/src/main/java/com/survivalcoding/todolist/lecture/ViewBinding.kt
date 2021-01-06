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

private lateinit var binding: ActivityLectureBinding
private lateinit var itemWeatherBinding: ItemWeatherBinding

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


        binding = ActivityLectureBinding.inflate(layoutInflater)
        val view = binding.root
        itemWeatherBinding = ItemWeatherBinding.inflate(LayoutInflater.from(view.context), view, false)
        setContentView(view)

        binding.listView.adapter = CustomAdapter2(data)
        binding.listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "$position 번째 클릭", Toast.LENGTH_SHORT).show()
        }
    }
}

data class Weather2(val image: String, val city: String, val temp: String)

class CustomAdapter2(private val data: List<Weather>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(parent!!.context).inflate(R.layout.item_todo, parent, false)

        val currentWeather = getItem(position)

        itemWeatherBinding.cityText.text = currentWeather.city
        itemWeatherBinding.tempText.text = currentWeather.temp

        return view
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size
}
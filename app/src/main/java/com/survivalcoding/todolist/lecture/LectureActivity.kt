package com.survivalcoding.todolist.lecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.todolist.R

class Lecture : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecture)

        // 어댑터 뷰 사용
        /*
        준비물
        1. 데이터
        2. 어댑터
        3. 뷰
         */

        // 1. 데이터 (0..30) : IntRange, data : List<String>
        val data = (0..30).map { item -> item.toString() }.toList();

        // 2. 어댑터 준비 - ArrayAdapter(context, layout, data)
        // 나만의 어댑터를 준비하지 않고, 그냥 여기에 [R.layout.List에 들어갈 하나의 레이아웃 파일]을 넣으면 안되나..? >> 밑에서 해보자.
        // 에러가 생긴다. java.lang.IllegalStateException: ArrayAdapter requires the resource ID to be a TextView
        // 아무래도 ArrayAdapter라서 class가 아닌, 하나의 참조타입인 Array만 넣어야 하는 것 같다.
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)

        // 3. View - View에 어댑터 연결
        val listView: ListView =
            findViewById(R.id.listView)   // 왜 ListView가 제네릭처럼 들어가는거지...? >> findViewById가 반환형으로 제네릭을 필요로 한다.
        listView.adapter = adapter // listView.setAdapter(adapter)

        // 클릭 이벤트 추가
        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "$position 아이템 선", Toast.LENGTH_SHORT).show()
        }

        /*   --------- weather를 넣어서 adapter를 사용해 보자. -------------- */

        // 1. data 준비
        val Data = listOf<Weather>(
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

        // 2. 어댑터 준비
        val customAdapter = CustomAdapter(Data)
//        val Adapter = ArrayAdapter(this, R.layout.activity_weather, Data)
        listView.adapter = customAdapter

    }
}

data class Weather(val image: String, val city: String, val temp: String)

// View holder class 생성 >> 해당 클래스에 레이아웃 요소들의 값을이 저장된다.
class WeatherViewHolder {
    lateinit var weatherInfo: ImageView
    lateinit var cityText: TextView
    lateinit var tempText: TextView
}

class CustomAdapter constructor(private val weather: List<Weather>) : BaseAdapter() {

    // getView메소드는 리스트의 값이 있을 때 마다 호출된다.

    /* 뷰홀더 패턴 -> getView를 불러올 때 마다 findViewById하지말고, 처음에만 findViewById를 하고, 다음번에는 그냥 그 값을 불러만 오도록 하자. */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: WeatherViewHolder

        if (convertView == null) {
            // 레이아웃을 들고와서 View타입인 view변수에 저장한다.
            view =
                LayoutInflater.from(parent!!.context).inflate(R.layout.item_weather, parent, false)

            // 뷰홀더 패턴 convertView == null일 때, 각각 레이아웃 요소들을 받는다.
            holder = WeatherViewHolder()
            holder.weatherInfo = view.findViewById<ImageView>(R.id.imageView)
            holder.cityText = view.findViewById<TextView>(R.id.cityText)
            holder.tempText = view.findViewById<TextView>(R.id.tempText)
            view.tag = holder   // view의 tag를 이용해서 레이아웃 요소들을 저장한다.
        } else {
            view = convertView  // convertView는 null이 아니라는 것이 보장되었다.
            holder = view.tag as WeatherViewHolder  // tag는 object type이라서 형변환 해 주어야 한다.
        }

        // 각각의 인스턴스를 받는다 >> getView는 리스트를 불러올 때 마다 호출되고
        // findViewById는 비용이 많이 드는 작업이라서 이는 나중에 최적화 해야한다.
//        val weatherInfo = view.findViewById<ImageView>(R.id.imageView)
//        val cityText = view.findViewById<TextView>(R.id.cityText)
//        val tempText = view.findViewById<TextView>(R.id.tempText)

        val currentWeather = getItem(position)  // getItem의 반환형이 Any라면, [as Weather]로 형변환 해야한다.

//        cityText.text = currentWeather.city
//        tempText.text = currentWeather.temp

        holder.cityText.text = currentWeather.city
        holder.tempText.text = currentWeather.temp

        return view
    }

    override fun getItem(position: Int) = weather[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount(): Int {
        return weather.size
    }
    // override fun getCount() = weather.size 로 작성될 수 있음.

}
package com.example.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class WeatherAdapter(
    val activity: MainActivity, private var weatherList: ArrayList<WeatherModel>?
) : RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {
    lateinit var context: Context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txt_climate2: TextView = itemView.findViewById(R.id.txt_climate2)
        var txt_humadity2: TextView = itemView.findViewById(R.id.txt_humadity2)
        var txt_pressure2: TextView = itemView.findViewById(R.id.txt_pressure2)
        var txt_wind2: TextView = itemView.findViewById(R.id.txt_wind2)
        var image2: ImageView = itemView.findViewById(R.id.image2)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.MyViewHolder {

        context = parent.context
        val v: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_weather, null)
        return MyViewHolder(v)

    }

    override fun getItemCount(): Int {
        return weatherList!!.size
    }

    override fun onBindViewHolder(holder: WeatherAdapter.MyViewHolder, position: Int) {

        holder.txt_climate2.text = weatherList!!.get(position).main
        holder.txt_humadity2.text = weatherList!!.get(position).humidity
        holder.txt_pressure2.text = weatherList!!.get(position).pressure
        holder.txt_wind2.text = weatherList!!.get(position).wind

        when (weatherList!!.get(position).icon) {
            "01d" -> holder.image2.setImageResource(R.drawable.sunny)
            "02d" -> holder.image2.setImageResource(R.drawable.cloud)
            "03d" -> holder.image2.setImageResource(R.drawable.cloud)
            "04d" -> holder.image2.setImageResource(R.drawable.cloud)
            "04n" -> holder.image2.setImageResource(R.drawable.cloud)
            "10d" -> holder.image2.setImageResource(R.drawable.rain)
            "11d" -> holder.image2.setImageResource(R.drawable.storm)
            "13d" -> holder.image2.setImageResource(R.drawable.snowflake)
            "01n" -> holder.image2.setImageResource(R.drawable.cloud)
            "02n" -> holder.image2.setImageResource(R.drawable.cloud)
            "03n" -> holder.image2.setImageResource(R.drawable.cloud)
            "10n" -> holder.image2.setImageResource(R.drawable.cloud)
            "11n" -> holder.image2.setImageResource(R.drawable.rain)
            "13n" -> holder.image2.setImageResource(R.drawable.snowflake)
        }

    }
}
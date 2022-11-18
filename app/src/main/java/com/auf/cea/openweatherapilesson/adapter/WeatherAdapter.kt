package com.auf.cea.openweatherapilesson.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.auf.cea.openweatherapilesson.MainActivity
import com.auf.cea.openweatherapilesson.ViewMoreActivity
import com.auf.cea.openweatherapilesson.constants.BASE_IMAGE_URL
import com.auf.cea.openweatherapilesson.constants.MODEL_DATA
import com.auf.cea.openweatherapilesson.databinding.ContentForecastRvBinding
import com.auf.cea.openweatherapilesson.models.ForecastModel
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherAdapter(private var forecastList: ArrayList<ForecastModel>, private var context :Context) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>(){

    inner class WeatherViewHolder(private val binding: ContentForecastRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (itemData: ForecastModel){
            val mainWeatherData = itemData.weather[0]
            binding.txtWeatherType.text = mainWeatherData.main
            binding.txtTemp.text = String.format("%s˚C",itemData.main.temp)
            binding.txtMinTemp.text = String.format("Min temp: %s˚C",itemData.main.temp_min)
            binding.txtMaxTemp.text = String.format("Max temp: %s˚C", itemData.main.temp_max)
            binding.txtHumidity.text = String.format("Humidity: %s%%", itemData.main.humidity)
            binding.txtFeelsLike.text = String.format("Feels like: %s˚C",itemData.main.feels_like)
            binding.txtDay.text = getDay(itemData.dt)
            binding.txtTime.text = getTime(itemData.dt)
            Glide.with(context)
                .load(BASE_IMAGE_URL+mainWeatherData.icon+".png")
                .override(200,200)
                .into(binding.imgWeather)

            binding.cvForecast.setOnClickListener{
                val intent = Intent(context, ViewMoreActivity::class.java)
                intent.putExtra(MODEL_DATA, itemData)
                context.startActivity(intent)
                Log.d(MainActivity::class.java.simpleName.toString(),itemData.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ContentForecastRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val forecastData = forecastList[position]
        holder.bind(forecastData)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    fun getDay(timeStamp: Long): String{
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timeStamp * 1000)
    }

    fun getTime(timeStamp: Long): String{
        return SimpleDateFormat("hh:mm aa", Locale.ENGLISH).format(timeStamp * 1000)
    }

    fun updateData(forecastList: ArrayList<ForecastModel>){
        this.forecastList = arrayListOf()
        notifyDataSetChanged()
        this.forecastList = forecastList
        this.notifyItemInserted(this.forecastList.size)
    }
}
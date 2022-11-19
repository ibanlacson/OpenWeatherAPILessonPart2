package com.auf.cea.openweatherapilesson.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auf.cea.openweatherapilesson.MainActivity
import com.auf.cea.openweatherapilesson.ViewMoreActivity
import com.auf.cea.openweatherapilesson.constants.BASE_IMAGE_URL
import com.auf.cea.openweatherapilesson.constants.MAIN_DATA
import com.auf.cea.openweatherapilesson.constants.MODEL_DATA
import com.auf.cea.openweatherapilesson.databinding.ContentForecastRvBinding
import com.auf.cea.openweatherapilesson.models.ForecastModel
import com.auf.cea.openweatherapilesson.models.MainForecastModel
import com.auf.cea.openweatherapilesson.services.helper.GeneralHelper
import com.bumptech.glide.Glide
import kotlin.collections.ArrayList

class WeatherAdapter(private var forecastList: ArrayList<ForecastModel>, private var context :Context) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>(){

    private lateinit var mainForecastModel : ArrayList<MainForecastModel>

    inner class WeatherViewHolder(private val binding: ContentForecastRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (itemData: ForecastModel){
            val mainWeatherData = itemData.weather[0]
            binding.txtWeatherType.text = mainWeatherData.main
            binding.txtTemp.text = String.format("%s˚C",itemData.main.temp)
            binding.txtMinTemp.text = String.format("Min temp: %s˚C",itemData.main.temp_min)
            binding.txtMaxTemp.text = String.format("Max temp: %s˚C", itemData.main.temp_max)
            binding.txtHumidity.text = String.format("Humidity: %s%%", itemData.main.humidity)
            binding.txtFeelsLike.text = String.format("Feels like: %s˚C",itemData.main.feels_like)
            binding.txtDay.text = GeneralHelper.getDay(itemData.dt)
            binding.txtTime.text = GeneralHelper.getTime(itemData.dt)
            Glide.with(context)
                .load(BASE_IMAGE_URL+mainWeatherData.icon+".png")
                .override(200,200)
                .into(binding.imgWeather)

            binding.cvForecast.setOnClickListener{
                val mainForecastModelData: MainForecastModel = mainForecastModel[0]

                val intent = Intent(context, ViewMoreActivity::class.java)
                intent.putExtra(MODEL_DATA, itemData)
                intent.putExtra(MAIN_DATA,mainForecastModelData)
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

    fun updateData(forecastList: ArrayList<ForecastModel>, mainForecastData: ArrayList<MainForecastModel>){
        this.forecastList = arrayListOf()
        this.mainForecastModel = arrayListOf()
        notifyDataSetChanged()
        this.forecastList = forecastList
        this.mainForecastModel = mainForecastData
        this.notifyItemInserted(this.forecastList.size)
    }
}


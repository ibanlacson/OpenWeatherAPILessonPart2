package com.auf.cea.openweatherapilesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auf.cea.openweatherapilesson.adapter.WeatherAdapter
import com.auf.cea.openweatherapilesson.constants.API_KEY
import com.auf.cea.openweatherapilesson.constants.BASE_IMAGE_URL
import com.auf.cea.openweatherapilesson.databinding.ActivityMainBinding
import com.auf.cea.openweatherapilesson.models.ForecastModel
import com.auf.cea.openweatherapilesson.models.LocationModel
import com.auf.cea.openweatherapilesson.services.helper.RetrofitHelper
import com.auf.cea.openweatherapilesson.services.repository.OpenWeatherAPI
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeatherAdapter
    private lateinit var forecastData: ArrayList<ForecastModel>
    private lateinit var locationList: ArrayList<LocationModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Weather Application by Group 1"

        forecastData = arrayListOf()
        adapter = WeatherAdapter(forecastData,this)

        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.rvForecast.layoutManager = layoutManager
        binding.rvForecast.adapter = adapter

        locationList = arrayListOf(
            LocationModel("Angeles City",15.1463554, 120.5245999),
            LocationModel("Metro Manila", 14.5965788,120.9445407),
            LocationModel("Cebu City",7.8370652,122.3735825)
        )

        val cityList = arrayOf("Angeles City", "Metro Manila", "Cebu City")

        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, cityList)
        binding.spnLocation.adapter = spinnerAdapter
        binding.spnLocation.onItemSelectedListener = this

    }

    private fun getWeatherData(lat: Double, lon : Double){
        val weatherAPI = RetrofitHelper.getInstance().create(OpenWeatherAPI::class.java)

        GlobalScope.launch(Dispatchers.IO){
            val result = weatherAPI.getFiveDayForecast(lat,lon, API_KEY, "metric")
            val weatherData = result.body()

            if(weatherData != null){
                forecastData.clear()
                forecastData.addAll(weatherData.list)
                withContext(Dispatchers.Main){
                    adapter.updateData(forecastData)
                    updateBubbleDisplay(forecastData[0])
                }
            }
        }
    }

    private fun updateBubbleDisplay (modelData: ForecastModel){
        val weatherData = modelData.weather[0]
        with(binding){
            txtWeatherType.text = weatherData.main
            txtTemp.text = String.format("%s˚C",modelData.main.temp)
            txtFeelsLike.text = String.format("Feels like: %s˚C",modelData.main.feels_like)
            txtMinTemp.text = String.format("Min Temp:%s˚C",modelData.main.temp_min)
            txtMaxTemp.text = String.format("Max Temp:%s˚C",modelData.main.temp_max)
            txtHumidity.text = String.format("Humidity: %s%%", modelData.main.humidity)
            txtDay.text = adapter.getDay(modelData.dt)
            txtTime.text = adapter.getTime(modelData.dt)
            Glide.with(this@MainActivity)
                .load(BASE_IMAGE_URL+weatherData.icon+".png")
                .override(200, 200)
                .into(imgBubbleIcon)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val locationData = locationList[p2]
        getWeatherData(locationData.lat,locationData.lon)
        showAnimation()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun showAnimation(){

        with(binding.animationLoading){
            isGone = false
            playAnimation()
        }

        object : CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {

            }
            override fun onFinish() {
                with(binding.animationLoading){
                    isGone = true
                    cancelAnimation()
                }
            }
        }.start()
    }
}
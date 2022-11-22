package com.auf.cea.openweatherapilesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.auf.cea.openweatherapilesson.adapter.WeatherAdapter
import com.auf.cea.openweatherapilesson.constants.API_KEY
import com.auf.cea.openweatherapilesson.databinding.ActivityMainBinding
import com.auf.cea.openweatherapilesson.models.ForecastModel
import com.auf.cea.openweatherapilesson.models.LocationModel
import com.auf.cea.openweatherapilesson.models.MainForecastModel
import com.auf.cea.openweatherapilesson.services.helper.GeneralHelper
import com.auf.cea.openweatherapilesson.services.helper.ImageHelper
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
    private lateinit var mainForecastData: ArrayList<MainForecastModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Weather Application by Group 1"

        forecastData = arrayListOf()
        mainForecastData = arrayListOf()

        adapter = WeatherAdapter(forecastData,this)

        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.rvForecast.layoutManager = layoutManager
        binding.rvForecast.adapter = adapter

        locationList = arrayListOf(
            LocationModel("Angeles City",15.145549, 120.5946859),
            LocationModel("Makati City", 14.5546597,121.0243391),
            LocationModel("Cebu City",10.315542,123.8848808)
        )

        val cityList = arrayOf("Angeles City", "Makati City", "Cebu City")

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


                mainForecastData.clear()
                mainForecastData.add(weatherData)

                // Log.d(MainActivity::class.java.simpleName.toString(),mainForecastModel.toString())

                forecastData.clear()
                forecastData.addAll(weatherData.list)
                withContext(Dispatchers.Main){
                    adapter.updateData(forecastData, mainForecastData)
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
            txtDay.text = GeneralHelper.getDay(modelData.dt)
            txtTime.text = GeneralHelper.getTime(modelData.dt)
            val baseImageURL = ImageHelper.getImageLink(weatherData.main, modelData.sys.pod)
            Glide.with(this@MainActivity)
                .load(baseImageURL)
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
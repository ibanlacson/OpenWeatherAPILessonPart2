package com.auf.cea.openweatherapilesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.auf.cea.openweatherapilesson.constants.API_KEY
import com.auf.cea.openweatherapilesson.constants.HIGH_TEMP_ICON_URL
import com.auf.cea.openweatherapilesson.constants.LOW_TEMP_ICON_URL
import com.auf.cea.openweatherapilesson.databinding.ActivitySearchCityBinding
import com.auf.cea.openweatherapilesson.models.currentweather.CurrentWeatherModel
import com.auf.cea.openweatherapilesson.models.geocode.GeoCodingModelItem
import com.auf.cea.openweatherapilesson.services.helper.GeneralHelper
import com.auf.cea.openweatherapilesson.services.helper.ImageHelper
import com.auf.cea.openweatherapilesson.services.helper.RetrofitHelper
import com.auf.cea.openweatherapilesson.services.repository.CurrentWeatherAPI
import com.auf.cea.openweatherapilesson.services.repository.GeoCodeAPI
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchCityActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivitySearchCityBinding
    private lateinit var geoCodingData : GeoCodingModelItem
    private var cityName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "City Forecast"
        binding.btnSearch.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            (R.id.btn_search) -> {
                var inputCity = binding.txtSearchCity.text.toString()
                if (inputCity.isEmpty()) {
                    binding.txtSearchCity.error = "Required"
                    return
                } else {
                    inputCity = inputCity.replace(" City","",true)
                    cityName = "$inputCity City"
                    getCoordinates(inputCity)
                    showLoadingAnimation()
                }

            }
        }
    }

    private fun getCurrentWeatherData(lat: Double, lon:Double){
        val currentWeatherAPI = RetrofitHelper.getInstance().create(CurrentWeatherAPI::class.java)

        GlobalScope.launch(Dispatchers.IO){
            val currentWeatherResult = currentWeatherAPI.getCurrentWeather(lat,lon, API_KEY, "metric")
            val currentWeatherData = currentWeatherResult.body()

            if(currentWeatherData != null) {
                withContext(Dispatchers.Main){
                    updateCityForecastView(currentWeatherData)
                }
            }
        }
    }

    private fun getCoordinates(city:String){
        val geoCodeAPI = RetrofitHelper.getInstance().create(GeoCodeAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val getCoordinatesResult = geoCodeAPI.getGeoCode(city,1, API_KEY)
            val geoCode = getCoordinatesResult.body()

            if(geoCode != null) {
                geoCodingData = geoCode[0]
                withContext(Dispatchers.Main){
                    val inputLat =geoCodingData.lat
                    val inputLon =geoCodingData.lon

                    getCurrentWeatherData(inputLat,inputLon)
                }
            }
        }
    }

    private fun updateCityForecastView(forecastData:CurrentWeatherModel){
        binding.llCityView.isVisible = true
        val dateValue = GeneralHelper.getDate(forecastData.dt)
        val dayValue = GeneralHelper.getDay(forecastData.dt)
        val timeValue = GeneralHelper.getTime(forecastData.dt)
        val weatherData = forecastData.weather[0]
        with(binding){
            // LL1
            txtCity.text = cityName
            txtTimeAndDate.text = String.format("%s | %s | %s", dateValue, dayValue, timeValue)

            // LL2
            txtTemp.text = String.format("%s°C",forecastData.main.temp)
            txtWeatherType.text = weatherData.main
            val baseImageURL = ImageHelper.getImageLink(weatherData.main, GeneralHelper.getPOD(forecastData.dt))
            Glide.with(this@SearchCityActivity)
                .load(baseImageURL)
                .into(imgCurrentIcon)

            // LL3 - Row 1
            txtMinTemp.text = String.format("%s°C", forecastData.main.temp_min)
            txtMaxTemp.text = String.format("%s°C", forecastData.main.temp_max)
            txtHumidity.text = String.format("%s%%", forecastData.main.humidity)

            Glide.with(this@SearchCityActivity)
                .load(LOW_TEMP_ICON_URL)
                .into(imgMinTemp)

            Glide.with(this@SearchCityActivity)
                .load(HIGH_TEMP_ICON_URL)
                .into(imgMaxTemp)

            // LL3 - Row 2
            txtWindSpeed.text = String.format("%s m/s", forecastData.wind.speed)
            txtCloudiness.text = String.format("%s%%",forecastData.clouds.all)
            txtVisibility.text = String.format("%s km",forecastData.visibility/1000)


            // LL3 - Row 3
            txtPressure.text = String.format("%s hPa", forecastData.main.pressure)
            txtSunriseTime.text = GeneralHelper.getTime(forecastData.sys.sunrise)
            txtSunsetTime.text = GeneralHelper.getTime(forecastData.sys.sunset)
        }
    }

    private fun showLoadingAnimation() {
        // Initial

        binding.llCityView.isGone = false
        binding.llCityViewContent.isGone = true

        with(binding.animationLoading) {
            isGone = false
            playAnimation()
        }

        object : CountDownTimer(3000,1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                binding.llCityView.isGone = false
                binding.llCityViewContent.isGone = false

                with(binding.animationLoading) {
                    isGone = true
                    cancelAnimation()
                }
            }

        }.start()
    }
}
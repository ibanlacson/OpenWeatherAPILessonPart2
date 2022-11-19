package com.auf.cea.openweatherapilesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.auf.cea.openweatherapilesson.constants.BASE_IMAGE_URL
import com.auf.cea.openweatherapilesson.constants.MAIN_DATA
import com.auf.cea.openweatherapilesson.constants.MODEL_DATA
import com.auf.cea.openweatherapilesson.databinding.ActivityViewMoreBinding
import com.auf.cea.openweatherapilesson.models.ForecastModel
import com.auf.cea.openweatherapilesson.models.Main
import com.auf.cea.openweatherapilesson.models.MainForecastModel
import com.auf.cea.openweatherapilesson.services.helper.GeneralHelper
import com.bumptech.glide.Glide

class ViewMoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewMoreBinding
    private lateinit var modelData : ForecastModel
    private lateinit var mainData : MainForecastModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null) {
            modelData = intent.getSerializableExtra(MODEL_DATA) as ForecastModel
            mainData = intent.getSerializableExtra(MAIN_DATA) as MainForecastModel

            val weatherData = modelData.weather[0]
            val dayValue = GeneralHelper.getDay(modelData.dt)
            val timeValue = GeneralHelper.getTime(modelData.dt)
            val dateValue = GeneralHelper.getDate(modelData.dt)

            supportActionBar?.title = String.format("%s %s Forecast", dayValue, timeValue)

            val popValue = GeneralHelper.getPop(mainData.city.population)

            with(binding){

                // LL1
                txtCity.text = mainData.city.name
                txtTimeAndDate.text = String.format("%s, %s | %s", dateValue, dayValue, timeValue)

                // LL2
                txtTemp.text = String.format("%s°C",modelData.main.temp)
                txtWeatherType.text = weatherData.main
                txtCollatedTemp.text = String.format("Min: %s°C | Max: %s°C",modelData.main.temp_min,modelData.main.temp_max)
                Glide.with(this@ViewMoreActivity)
                    .load(BASE_IMAGE_URL+weatherData.icon+"@2x.png")
                    .override(200, 200)
                    .into(imgIcon)

                // LL3 - Row 1
                txtWindSpeed.text = String.format("%s m/s", modelData.wind.speed)
                txtPrecipitationRate.text = String.format("%s%%", modelData.pop * 100)
                txtHumidity.text = String.format("%s%%", modelData.main.humidity)

                // LL3 - Row 2
                txtCloudiness.text = String.format("%s%%",modelData.clouds.all)
                txtVisibility.text = String.format("%s km",modelData.visibility/1000)
                txtPressure.text = String.format("%s hPa", modelData.main.pressure)

                // LL3 - Row 3
                txtSunriseTime.text = GeneralHelper.getTime(mainData.city.sunrise)
                txtSunsetTime.text = GeneralHelper.getTime(mainData.city.sunset)
                txtPopulation.text = popValue

            }

        }

    }
}
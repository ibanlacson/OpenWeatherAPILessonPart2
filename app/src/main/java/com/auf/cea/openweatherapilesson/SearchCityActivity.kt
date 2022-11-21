package com.auf.cea.openweatherapilesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.auf.cea.openweatherapilesson.constants.API_KEY
import com.auf.cea.openweatherapilesson.databinding.ActivitySearchCityBinding
import com.auf.cea.openweatherapilesson.models.geocode.GeoCodingModelItem
import com.auf.cea.openweatherapilesson.services.helper.RetrofitHelper
import com.auf.cea.openweatherapilesson.services.repository.GeoCodeAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchCityActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivitySearchCityBinding
    private lateinit var geoCodingData : GeoCodingModelItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchCityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSearch.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            (R.id.btn_search) -> {
                var inputCity = binding.txtSearchCity.text.toString()
                getCoordinates(inputCity)
            }
        }
    }

    private fun getCurrentWeatherData(lat: Double, lon:Double){

    }

    private fun getCoordinates(city:String){
        val geoCodeAPI = RetrofitHelper.getInstance().create(GeoCodeAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val result = geoCodeAPI.getGeoCode(city,1, API_KEY)
            val geoCode = result.body()

            if(geoCode != null) {
                geoCodingData = geoCode[0]
                withContext(Dispatchers.Main){
                    binding.txtLatLon.text = String.format("Lat: %s | Lon: %s", geoCodingData.lat, geoCodingData.lon)
                }
            }
        }
    }
}
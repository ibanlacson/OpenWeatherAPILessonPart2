package com.auf.cea.openweatherapilesson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import com.auf.cea.openweatherapilesson.constants.MODEL_DATA
import com.auf.cea.openweatherapilesson.databinding.ActivityViewMoreBinding
import com.auf.cea.openweatherapilesson.models.ForecastModel

class ViewMoreActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewMoreBinding
    private lateinit var modelData : ForecastModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null) {
            modelData = intent.getSerializableExtra(MODEL_DATA) as ForecastModel



            //Log.d(ViewMoreActivity::class.java.simpleName,modelData.toString())
        }

    }
}
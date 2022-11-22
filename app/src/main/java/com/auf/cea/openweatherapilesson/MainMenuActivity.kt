package com.auf.cea.openweatherapilesson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.auf.cea.openweatherapilesson.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main_menu)

        binding.btnFiveDay.setOnClickListener(this)
        binding.btnSearchCity.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            (R.id.btn_five_day) -> {
                 val intent = Intent(this@MainMenuActivity,MainActivity::class.java)
//                intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                Log.d(MainMenuActivity::class.java.simpleName, "Napindot yung five day")

            }
            (R.id.btn_search_city) -> {
                 val intent = Intent(this@MainMenuActivity,SearchCityActivity::class.java)
//                intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                Log.d(MainMenuActivity::class.java.simpleName, "Napindot yung current")
            }
        }
    }
}
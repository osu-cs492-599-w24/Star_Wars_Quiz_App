package com.example.starwarsquiz

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.starwarsquiz.data.SWAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private val swapiService = SWAPIService.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainTV: TextView = findViewById(R.id.tv_main_activity)

        mainTV.text = "Star Wars App"
        doCharacterSearch(1, 10)
    }

    private fun doCharacterSearch(page: Int, limit: Int) {
        swapiService.loadCharacters(page, limit).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("Main Activity", "Status code: ${response.code()}")
                Log.d("Main Activity", "Body: ${response.body()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("Main Activity", "Error making call: ${t.message}")
            }

        })
    }
}
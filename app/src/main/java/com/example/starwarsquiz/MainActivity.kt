package com.example.starwarsquiz

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.starwarsquiz.data.SWAPICharacterResults
import com.example.starwarsquiz.data.SWAPIService
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
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
        swapiService.loadCharacters(page, limit).enqueue(object : Callback<SWAPICharacterResults> {
            override fun onResponse(call: Call<SWAPICharacterResults>, response: Response<SWAPICharacterResults>) {
                Log.d("Main Activity", "Status code: ${response.code()}")
                Log.d("Main Activity", "Body: ${response.body()}")
                if (response.isSuccessful) {
                    Log.d("Main Activity", "Name: ${response.body()?.results?.get(0)!!.name}")
                }
            }

            override fun onFailure(call: Call<SWAPICharacterResults>, t: Throwable) {
                Log.d("Main Activity", "Error making call: ${t.message}")
            }

        })
    }
}
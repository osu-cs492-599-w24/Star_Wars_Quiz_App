package com.example.starwarsquiz.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.SWAPIService

class MainActivity : AppCompatActivity() {
    private val swapiService = SWAPIService.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val swapiCharacterTV: TextView = findViewById(R.id.tv_swapi_character)
//        doCharacterSearch(1, 10)
        swapiCharacterTV.text = "character"
    }

//    private fun doCharacterSearch(page: Int, limit: Int) {
//        swapiService.loadCharacters(page, limit).enqueue(object : Callback<SWAPICharacterResults> {
//            override fun onResponse(call: Call<SWAPICharacterResults>, response: Response<SWAPICharacterResults>) {
//                Log.d("Main Activity", "Status code: ${response.code()}")
//                Log.d("Main Activity", "Body: ${response.body()}")
//                if (response.isSuccessful) {
//                    Log.d("Main Activity", "Name: ${response.body()?.results?.get(0)!!.name}")
//                }
//            }
//
//            override fun onFailure(call: Call<SWAPICharacterResults>, t: Throwable) {
//                Log.d("Main Activity", "Error making call: ${t.message}")
//            }
//        })
//
//    }
}
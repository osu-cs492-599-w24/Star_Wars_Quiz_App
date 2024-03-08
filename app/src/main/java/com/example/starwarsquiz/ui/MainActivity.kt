package com.example.starwarsquiz.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.SWAPIService

class MainActivity : AppCompatActivity() {
    private val viewModel: SWAPICharacterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val swapiCharacterTV: TextView = findViewById(R.id.tv_swapi_character)
        viewModel.loadSWAPICharacters(1, 10)
        swapiCharacterTV.text = "character"
    }
}
package com.example.starwarsquiz

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainTV: TextView = findViewById(R.id.tv_main_activity)

        mainTV.text = "Star Wars App"
    }
}
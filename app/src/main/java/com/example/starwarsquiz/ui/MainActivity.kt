package com.example.starwarsquiz.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsquiz.R

class MainActivity : AppCompatActivity() {
    private val characterListViewModel: SWAPICharacterViewModel by viewModels()
    private val characterDetailsViewModel: SWAPICharacterDetailsViewModel by viewModels()
    private val planetsViewModel: SWAPIPlanetViewModel by viewModels()
    private val planetDetailsViewModel: SWAPIPlanetDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Check the Network Inspector and perform a rotation to view that these API calls
         * are successful and look at the Logcat to see that the calls are being parsed
         * under the tag "ViewModel".
         */
        characterListViewModel.loadSWAPICharacters(1, 10)
        characterDetailsViewModel.loadSWAPICharactersDetails(1)
        planetsViewModel.loadSWAPIPlanets(1, 10)
        planetDetailsViewModel.loadSWAPIPlanetDetails(1)
    }
}
package com.example.starwarsquiz.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.starwarsquiz.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val characterListViewModel: SWAPICharacterViewModel by viewModels()
    private val characterDetailsViewModel: SWAPICharacterDetailsViewModel by viewModels()
    private val planetsViewModel: SWAPIPlanetViewModel by viewModels()
    private val planetDetailsViewModel: SWAPIPlanetDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set up bottom nav menu
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)
        bottomNav.setOnApplyWindowInsetsListener { myView, insets ->
            myView.updatePadding(bottom = 0)
            insets
        }

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
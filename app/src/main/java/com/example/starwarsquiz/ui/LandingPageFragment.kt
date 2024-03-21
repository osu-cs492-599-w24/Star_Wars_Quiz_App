package com.example.starwarsquiz.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.CharacterDetails
import com.example.starwarsquiz.data.PlanetDetails
import com.example.starwarsquiz.data.QuestionContents

import com.example.starwarsquiz.data.SWAPICharacter
import kotlin.random.Random
import com.example.starwarsquiz.data.SWAPIPlanet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.progressindicator.CircularProgressIndicator

class LandingPageFragment : Fragment(R.layout.fragment_landing_page){

    private val planetsViewModel: SWAPIPlanetViewModel by viewModels()

    private lateinit var startButton: Button

    private val characterDetailsViewModel: SWAPICharacterDetailsViewModel by viewModels()
    private val planetDetailsViewModel: SWAPIPlanetDetailsViewModel by viewModels()

    private var characterDetails: CharacterDetails? = null
    private var planetDetails: PlanetDetails? = null
    private var listSize = 1..50
    private val randomNumber = generateRandomNumber(listSize, 17)
    private lateinit var loadingIndicator: CircularProgressIndicator
    private lateinit var loadingErrorTV: TextView

    private lateinit var landingPageView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set up bottom nav menu
        val navController = findNavController()
        val bottomNav: BottomNavigationView = view.findViewById(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)
        bottomNav.setOnApplyWindowInsetsListener { myView, insets ->
            myView.updatePadding(bottom = 0)
            insets
        }


        landingPageView = view.findViewById(R.id.landing_page_layout)
        startButton = view.findViewById(R.id.start_button)
        loadingIndicator = view.findViewById(R.id.loading_indicator)
        loadingErrorTV = view.findViewById(R.id.tv_loading_error)

        // Wait for both character and planet details to be loaded before starting the quiz
        characterDetailsViewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                planetDetailsViewModel.loading.observe(viewLifecycleOwner) { loading ->
                    if (loading) {
                        loadingIndicator.visibility = View.VISIBLE
                        landingPageView.visibility = View.INVISIBLE
                    } else {
                        loadingIndicator.visibility = View.INVISIBLE
                        landingPageView.visibility = View.VISIBLE
                    }
                }
            }
        }

        characterDetailsViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                planetDetailsViewModel.error.observe(viewLifecycleOwner) { error ->
                    if (error != null) {
                        loadingErrorTV.text = getString(R.string.loading_error, error.message)
                        loadingErrorTV.visibility = View.VISIBLE
                        landingPageView.visibility = View.INVISIBLE
                        Log.e("LandingPageFragment",
                            "Error fetching from API: ${error.message}")
                    }
                }
            }
        }

        characterDetailsViewModel.characterDetails.observe(viewLifecycleOwner) { character ->
            if (character != null) {
                characterDetails = character
            } else {
                Log.d("MCFragment", "character details is null")
            }
        }

        planetDetailsViewModel.planetDetails.observe(viewLifecycleOwner) { planet ->
            if (planet != null) {
                planetDetails = planet
                Log.d("MCFragment", "planet details is not null")
            } else {
                Log.d("MCFragment", "planet details is null")
            }
        }
        planetsViewModel.loadSWAPIPlanets(1, 40)
        var planet = listOf<SWAPIPlanet>()

        // navigate to first question on start btn click
        startButton.setOnClickListener {

            planetsViewModel.planetList.observe(viewLifecycleOwner) { planetList->
                if(planetList != null){
                    planet = planetList
                }
                else{
                    Log.d("LandingPageFragment", "planetList is empty")
                }
            }

            Log.d("LandingPageFragment", planet[36-1].name)

            val newArgs = QuestionContents(
                quizNumber = 1,
                currentScore = 0,
                question = "What planet is Darth Maul from?",
                        correctAnswer = planet[36-1].name,
                answerChoices = listOf(planet[1-1].name, planet[36-1].name, planet[14-1].name, planet[8-1].name)
            )
            val action = LandingPageFragmentDirections.navigateToQuizQuestionMc(newArgs)
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()

        characterDetailsViewModel.loadSWAPICharactersDetails(randomNumber)

//        characterDetails?.homeworldId?.let { planetDetailsViewModel.loadSWAPIPlanetDetails(it) }
        planetDetailsViewModel.loadSWAPIPlanetDetails(1)
    }

    private fun generateRandomNumber(range: IntRange, excludedNumber: Int): Int {
        var randomNumber: Int
        do {
            randomNumber = Random.nextInt(range.first, range.last + 1)
        } while (randomNumber == excludedNumber)
        return randomNumber
    }
}

package com.example.starwarsquiz.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.QuestionContents
import com.example.starwarsquiz.data.SWAPIPlanet

class LandingPageFragment : Fragment(R.layout.fragment_landing_page){

    private val planetsViewModel: SWAPIPlanetViewModel by viewModels()

    private lateinit var startButton: Button
    private lateinit var historyButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startButton = view.findViewById(R.id.start_button)
        historyButton = view.findViewById(R.id.button_score_history)

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

        historyButton.setOnClickListener {
            val action = LandingPageFragmentDirections.navigateToScoreHistory()
            findNavController().navigate(action)
        }
    }
}

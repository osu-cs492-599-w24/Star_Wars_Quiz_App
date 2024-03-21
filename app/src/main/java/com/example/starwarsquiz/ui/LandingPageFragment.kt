package com.example.starwarsquiz.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.CharacterDetails
import com.example.starwarsquiz.data.PlanetDetails
import com.example.starwarsquiz.data.QuestionContents
import com.example.starwarsquiz.data.SWAPICharacter
import kotlin.random.Random

class LandingPageFragment : Fragment(R.layout.fragment_landing_page){
    private lateinit var startButton: Button
    private lateinit var historyButton: Button

    private val characterListViewModel: SWAPICharacterViewModel by viewModels()
    private val characterDetailsViewModel: SWAPICharacterDetailsViewModel by viewModels()
    private val planetDetailsViewModel: SWAPIPlanetDetailsViewModel by viewModels()

    private var characterList: List<SWAPICharacter>? = null
    private var characterDetails: CharacterDetails? = null
    private var planetDetails: PlanetDetails? = null
    private var listSize = 1..50
    private val randomNumber = generateRandomNumber(listSize, 17)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startButton = view.findViewById(R.id.start_button)
        historyButton = view.findViewById(R.id.button_score_history)

//        characterListViewModel.characterResults.observe(viewLifecycleOwner) { CharacterList ->
//            if (CharacterList != null) {
//                characterList = CharacterList
//            } else {
//                Log.d("MCFragment", "character list is null")
//            }
//        }

        // Wait for both character and planet details to be loaded before starting the quiz
        startButton.visibility = View.INVISIBLE
        characterDetailsViewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (!loading) {
                planetDetailsViewModel.loading.observe(viewLifecycleOwner) { loading ->
                    if (!loading) {
                        startButton.visibility = View.VISIBLE
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

        // navigate to first question on start btn click
        startButton.setOnClickListener {
            val newArgs = QuestionContents(
                1,
                0,
                "What planet is Darth Maul from?",
                "Tattooine",
                listOf("Tattooine", "Jeff", "Jeb", "Jeb!")
            )
            val action = LandingPageFragmentDirections.navigateToQuizQuestionMc(newArgs)
            findNavController().navigate(action)
        }

        historyButton.setOnClickListener {
            val action = LandingPageFragmentDirections.navigateToScoreHistory()
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

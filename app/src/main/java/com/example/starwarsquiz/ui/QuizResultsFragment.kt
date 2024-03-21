package com.example.starwarsquiz.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.CharacterDetails
import com.example.starwarsquiz.data.PlanetDetails
import com.example.starwarsquiz.data.QuestionContents
import com.example.starwarsquiz.data.QuizScoreEntity
import com.example.starwarsquiz.data.SWAPIPlanet
import kotlin.random.Random

class QuizResultsFragment : Fragment(R.layout.fragment_quiz_results) {
    private val args: QuizResultsFragmentArgs by navArgs()

    // declare necessary view models here
    private val quizScoreViewModel: QuizScoreViewModel by viewModels()
    private val characterDetailsViewModel: SWAPICharacterDetailsViewModel by viewModels()
    private val planetDetailsViewModel: SWAPIPlanetDetailsViewModel by viewModels()
    private val planetsViewModel: SWAPIPlanetViewModel by viewModels()

    private lateinit var finalScoreTV: TextView
    private lateinit var highestScoreTV: TextView
    private lateinit var homeButton: Button
    private lateinit var restartButton: Button
    private lateinit var shareButton: ImageButton
    private lateinit var rewardVV: VideoView

    private var characterDetails: CharacterDetails? = null
    private var planetDetails: PlanetDetails? = null
    private var listSize = 1..50
    private val randomNumber = generateRandomNumber(listSize)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finalScoreTV = view.findViewById(R.id.tv_final_score)
        highestScoreTV = view.findViewById(R.id.tv_highest_score)
        homeButton = view.findViewById(R.id.button_home)
        restartButton = view.findViewById(R.id.button_restart_quiz)
        shareButton = view.findViewById(R.id.button_share_score)
        rewardVV = view.findViewById(R.id.vv_reward_video)

        /*
            perform logic below
            eg. navigate to home on home btn click or first question on restart btn click
         */

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

        // Restart button goes to first question
        restartButton.setOnClickListener {
            planetsViewModel.planetList.observe(viewLifecycleOwner) { planetList->
                if(planetList != null){
                    planet = planetList
                }
                else{
                    Log.d("LandingPageFragment", "planetList is empty")
                }
            }

            val newArgs = QuestionContents(
                1,
                0,
                question = "What planet is Darth Maul from?",
                correctAnswer = planet[36-1].name,
                answerChoices = listOf(planet[1-1].name, planet[36-1].name, planet[14-1].name, planet[8-1].name)
            )
            val action = QuizResultsFragmentDirections.navigateToQuizQuestionMc(newArgs)
            findNavController().navigate(action)
        }

        // Home button goes to landing page
        homeButton.setOnClickListener {
            val action = QuizResultsFragmentDirections.navigateToLandingPage()
            findNavController().navigate(action)
        }

        shareButton.setOnClickListener {
            val shareText = getString(
                R.string.share_text,
                args.quizResults.toString()
            )
            val intent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,shareText)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, null))
        }

        quizScoreViewModel.highestScore.observe(viewLifecycleOwner) { highScore ->
            highestScoreTV.text = getString(R.string.highest_score, highScore.toString())
        }

        // Set the score text
        val score = args.quizResults
        finalScoreTV.text = getString(R.string.your_score, score.toString())

        // Tap-to-pause video
        rewardVV.setOnClickListener {
            if (rewardVV.isPlaying) {
                rewardVV.pause()
            } else {
                rewardVV.start()
            }
        }
        val scoreEntity = QuizScoreEntity(
            runId = 0,
            score = args.quizResults,
            timestamp = System.currentTimeMillis()
        )
        quizScoreViewModel.addQuizScore(scoreEntity)

        // If we're passed the quiz results, we can play the reward video
        val videoPath = getRewardVideo(score)

        rewardVV.setVideoPath(videoPath)

        rewardVV.start()
    }

    private fun getRewardVideo(score: Int): String {
        val videoResource = when (score) {
            in 0..1 -> R.raw.deathstar
            in 2..3 -> R.raw.father
            in 4..5 -> R.raw.order66
            in 6..7 -> R.raw.noconfidence
            else -> R.raw.highground
        }
        return "android.resource://" + requireActivity().packageName + "/" + videoResource
    }

    private fun generateRandomNumber(range: IntRange): Int {
        var randomNumber: Int
        do {
            randomNumber = Random.nextInt(range.first, range.last + 1)
        } while (randomNumber == 17)
        return randomNumber
    }
}
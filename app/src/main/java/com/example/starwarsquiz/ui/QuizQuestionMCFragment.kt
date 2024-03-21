package com.example.starwarsquiz.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.CharacterDetails
import com.example.starwarsquiz.data.PlanetDetails
import com.example.starwarsquiz.data.QuestionContents
import com.example.starwarsquiz.data.SWAPICharacter
import com.example.starwarsquiz.data.SWAPIPlanet
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlin.random.Random

class QuizQuestionMCFragment : Fragment(R.layout.fragment_quiz_question_mc){
    private val args: QuizQuestionMCFragmentArgs by navArgs()

    // declare necessary view models here
    private val characterDetailsViewModel: SWAPICharacterDetailsViewModel by viewModels()
    private val planetDetailsViewModel: SWAPIPlanetDetailsViewModel by viewModels()

    private val planetsViewModel: SWAPIPlanetViewModel by viewModels()
    private val resultViewModel: SWAPICharacterViewModel by viewModels()

    private lateinit var questionNumTV: TextView
    private lateinit var currentScoreTV: TextView
    private lateinit var questionTV: TextView
    private lateinit var submitButton: Button
    private lateinit var nextButton: Button

    private lateinit var mcChoice1: MaterialCardView
    private lateinit var tvChoice1: TextView
    private lateinit var mcChoice2: MaterialCardView
    private lateinit var tvChoice2: TextView
    private lateinit var mcChoice3: MaterialCardView
    private lateinit var tvChoice3: TextView
    private lateinit var mcChoice4: MaterialCardView
    private lateinit var tvChoice4: TextView

    private lateinit var loadingIndicator: CircularProgressIndicator
    private lateinit var mcPageView: View
    private lateinit var loadingErrorTV: TextView

    private var characterDetails: CharacterDetails? = null
    private var planetDetails: PlanetDetails? = null
    private var listSize = 1..50
    private val randomNumber = generateRandomNumber(listSize)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        planetsViewModel.loadSWAPIPlanets(1, 40)
        var planet = listOf<SWAPIPlanet>()

        resultViewModel.loadSWAPICharacters(1, 80)
        var people = listOf<SWAPICharacter>()

        var chosenAnswer = ""
        var showCorrect = false

        val greenColor = ContextCompat.getColor(requireContext(), R.color.sw_green)
        val yellowColor = ContextCompat.getColor(requireContext(), R.color.sw_yellow)
        val greyColor = ContextCompat.getColor(requireContext(), R.color.grey)

        questionNumTV = view.findViewById(R.id.tv_quiz_question_num)
        currentScoreTV = view.findViewById(R.id.tv_quiz_current_score)
        questionTV = view.findViewById(R.id.tv_quiz_question)
        submitButton = view.findViewById(R.id.button_submit)
        nextButton = view.findViewById(R.id.button_next)
        mcChoice1 = view.findViewById(R.id.mc_mc_answer1)
        tvChoice1 = view.findViewById(R.id.tv_mc_answer1)
        mcChoice2 = view.findViewById(R.id.mc_mc_answer2)
        tvChoice2 = view.findViewById(R.id.tv_mc_answer2)
        mcChoice3 = view.findViewById(R.id.mc_mc_answer3)
        tvChoice3 = view.findViewById(R.id.tv_mc_answer3)
        mcChoice4 = view.findViewById(R.id.mc_mc_answer4)
        tvChoice4 = view.findViewById(R.id.tv_mc_answer4)

        loadingIndicator = view.findViewById(R.id.loading_indicator)
        mcPageView = view.findViewById(R.id.question_mc_layout)

        // Hide the nav bar in the main activity
        val bottomNavBar = requireActivity().findViewById<View>(R.id.bottom_nav)
        bottomNavBar.visibility = View.GONE

        /*
            perform logic below

            eg. when submit is clicked, create snackbar indicating if answer was correct,
            then turn button invisible, and set 'next' button to visible to navigate to next question
            (or results screen if last question)

            since answer choices are in MC format, create a toggle function that changes the appearance of
            a clicked answer in the RV and compare it to the correct answer upon clicking submit
            (perform error handling when no answer is selected)
         */

        // If this is not the final question, show the next button
        if (args.questionContents.quizNumber < 10) {
            Log.d("QuizQuestionMCFragment", "Next button visible")
            nextButton.visibility = View.VISIBLE
            submitButton.visibility = View.INVISIBLE
        }

        // Set question number
        questionNumTV.text = args.questionContents.quizNumber.toString()

        // Set question
        questionTV.text = args.questionContents.question

        // Set current score
        currentScoreTV.text = args.questionContents.currentScore.toString()

        // Set answer list
        tvChoice1.text = args.questionContents.answerChoices?.get(0) ?: "NO VALUE"
        tvChoice2.text = args.questionContents.answerChoices?.get(1) ?: "NO VALUE"
        tvChoice3.text = args.questionContents.answerChoices?.get(2) ?: "NO VALUE"
        tvChoice4.text = args.questionContents.answerChoices?.get(3) ?: "NO VALUE"


        // Wait for both character and planet details to be loaded before starting the quiz
        characterDetailsViewModel.loading.observe(viewLifecycleOwner) { loadCharacter ->
            if (loadCharacter) {
                planetDetailsViewModel.loading.observe(viewLifecycleOwner) { loadPlanet ->
                    if (loadPlanet) {
                        loadingIndicator.visibility = View.VISIBLE
                        mcPageView.visibility = View.INVISIBLE
                    } else {
                        loadingIndicator.visibility = View.INVISIBLE
                        mcPageView.visibility = View.VISIBLE
                    }
                }
            }
        }

        characterDetailsViewModel.error.observe(viewLifecycleOwner) { characterError ->
            if (characterError != null) {
                planetDetailsViewModel.error.observe(viewLifecycleOwner) { planetError ->
                    if (planetError != null) {
                        loadingErrorTV.text = getString(R.string.loading_error, planetError.message)
                        loadingErrorTV.visibility = View.VISIBLE
                        mcPageView.visibility = View.INVISIBLE
                        Log.e("LandingPageFragment",
                            "Error fetching from API: ${planetError.message}")
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

        planetDetailsViewModel.planetDetails.observe(viewLifecycleOwner) { planetDetail ->
            if (planetDetail != null) {
                planetDetails = planetDetail
            } else {
                Log.d("MCFragment", "planet details is null")
            }
        }

        // Set answer choice click listeners
        // Color it yellow, uncolor the others
        mcChoice1.setOnClickListener {
            if (!showCorrect) {
                tvChoice1.setBackgroundColor(yellowColor)
                tvChoice2.setBackgroundColor(greyColor)
                tvChoice3.setBackgroundColor(greyColor)
                tvChoice4.setBackgroundColor(greyColor)
                chosenAnswer = tvChoice1.text.toString()
            }
        }
        mcChoice2.setOnClickListener {
            if (!showCorrect) {
                tvChoice2.setBackgroundColor(yellowColor)
                tvChoice1.setBackgroundColor(greyColor)
                tvChoice3.setBackgroundColor(greyColor)
                tvChoice4.setBackgroundColor(greyColor)
                chosenAnswer = tvChoice2.text.toString()
            }
        }
        mcChoice3.setOnClickListener {
            if (!showCorrect) {
                tvChoice3.setBackgroundColor(yellowColor)
                tvChoice1.setBackgroundColor(greyColor)
                tvChoice2.setBackgroundColor(greyColor)
                tvChoice4.setBackgroundColor(greyColor)
                chosenAnswer = tvChoice3.text.toString()
            }
        }
        mcChoice4.setOnClickListener {
            if (!showCorrect) {
                tvChoice4.setBackgroundColor(yellowColor)
                tvChoice1.setBackgroundColor(greyColor)
                tvChoice2.setBackgroundColor(greyColor)
                tvChoice3.setBackgroundColor(greyColor)
                chosenAnswer = tvChoice4.text.toString()
            }
        }

        // Submit button goes to results screen
        submitButton.setOnClickListener {
            if (showCorrect) {
                Log.d("QuizQuestionMCFragment", "Chosen answer: $chosenAnswer")

                val score = if (chosenAnswer == args.questionContents.correctAnswer) {
                    // If answer is correct, increment score
                    args.questionContents.currentScore + 1
                } else {
                    // If answer is incorrect, do not increment score
                    args.questionContents.currentScore
                }
                val action = QuizQuestionMCFragmentDirections.navigateToQuizResults(score)
                findNavController().navigate(action)
            }
            else {
                // If no answer is selected, show the correct answer
                Log.d("QuizQuestionMCFragment", "No answer selected")
                showCorrect = true
                when (args.questionContents.correctAnswer) {
                    tvChoice1.text.toString() -> tvChoice1.setBackgroundColor(greenColor)
                    tvChoice2.text.toString() -> tvChoice2.setBackgroundColor(greenColor)
                    tvChoice3.text.toString() -> tvChoice3.setBackgroundColor(greenColor)
                    tvChoice4.text.toString() -> tvChoice4.setBackgroundColor(greenColor)
                }
            }
        }

        // Next button goes to next question
        nextButton.setOnClickListener {
            if (showCorrect) {
                Log.d("QuizQuestionMCFragment", "Chosen answer: $chosenAnswer")

                val nextScore =
                    if (chosenAnswer == args.questionContents.correctAnswer) {
                        // If answer is correct, increment score
                        args.questionContents.currentScore + 1
                    } else {
                        // If answer is incorrect, do not increment score
                        args.questionContents.currentScore
                    }

                planetsViewModel.planetList.observe(viewLifecycleOwner) { planetList->
                    if(planetList != null){
                        planet = planetList
                    }
                    else{
                        Log.d("LandingPageFragment", "planetList is empty")
                    }
                }

                resultViewModel.characterResults.observe(viewLifecycleOwner) { characterResults->
                    if(characterResults != null){
                        people = characterResults
                    }
                    else{
                        Log.d("LandingPageFragment", "characterResults is empty")
                    }
                }

                //-2 for people after 17

                val nextQ : Int = args.questionContents.quizNumber + 1

                val newArgs : QuestionContents

                when (nextQ) {
                    1 -> {

                        newArgs = QuestionContents(
                            args.questionContents.quizNumber + 1,
                            nextScore,
                            "What planet is Darth Maul from?",
                            planet[36-1].name,
                            listOf(planet[1-1].name, planet[36-1].name, planet[14-1].name, planet[8-1].name)
                        )

                        val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                        findNavController().navigate(action)

                    }
                    2 -> {

                        newArgs = QuestionContents(
                            args.questionContents.quizNumber + 1,
                            nextScore,
                            "Who was Starkiller's master?",
                            people[4-1].name,
                            listOf(people[1-1].name, people[32-1].name, people[10-1].name, people[4-1].name)

                        )

                        val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionMc(newArgs)
                        findNavController().navigate(action)

                    }
                    3 -> {

                        newArgs = QuestionContents(
                            args.questionContents.quizNumber + 1,
                            nextScore,
                            "Who was the jedi that discovered Ahsoka?",
                            people[58-2].name,
                            listOf(people[11-1].name, people[53-2].name, people[10-1].name, people[58-2].name)

                        )

                        val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionMc(newArgs)
                        findNavController().navigate(action)

                    }
                    4 -> {

                        newArgs = QuestionContents(
                            args.questionContents.quizNumber + 1,
                            nextScore,
                            "Whose DNA was used to create the clone troopers?",
                            people[69-2].name,
                            listOf(people[69-2].name, people[72-2].name, people[22-2].name, people[67-2].name)

                        )

                        val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionMc(newArgs)
                        findNavController().navigate(action)

                    }
                    5 -> {

                        newArgs = QuestionContents(
                            args.questionContents.quizNumber + 1,
                            nextScore,
                            "Who was the only unaltered clone?",
                            people[22-2].name,
                            listOf(people[69-2].name, people[72-2].name, people[22-2].name, people[67-2].name)

                        )

                        val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionMc(newArgs)
                        findNavController().navigate(action)

                    }
                    6 -> {

                        newArgs = QuestionContents(
                            args.questionContents.quizNumber + 1,
                            nextScore,
                            "Who was Qui Gon Jinn's master?",
                            people[67-2].name,
                            listOf(people[1-1].name, people[20-2].name, people[67-2].name, people[10-1].name)

                        )

                        val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionMc(newArgs)
                        findNavController().navigate(action)

                    }
                    7 -> {

                        newArgs = QuestionContents(
                            args.questionContents.quizNumber + 1,
                            nextScore,
                            "What planet was Palpatine from?",
                            planet[8-1].name,
                            listOf(planet[1-1].name, planet[9-1].name, planet[7-1].name, planet[8-1].name)

                        )

                        val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionMc(newArgs)
                        findNavController().navigate(action)

                    }
                    8 -> {

                        newArgs = QuestionContents(
                            args.questionContents.quizNumber + 1,
                            nextScore,
                            "What planet was Starkiller from?",
                            planet[14-1].name,
                            listOf(planet[9-1].name, planet[14-1].name, planet[10-1].name, planet[5-1].name)

                        )

                        val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionMc(newArgs)
                        findNavController().navigate(action)

                    }
                    9 -> {

                        newArgs = QuestionContents(
                            args.questionContents.quizNumber + 1,
                            nextScore,
                            "Who inquired about the droid attack on the wookies?",
                            people[52-2].name,
                            listOf(people[52-2].name, people[57-2].name, people[58-2].name, people[51-2].name)

                        )

                        val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionMc(newArgs)
                        findNavController().navigate(action)

                    }
                    10 -> {

                        newArgs = QuestionContents(
                            args.questionContents.quizNumber + 1,
                            nextScore,
                            "Hello there! ...",
                            people[10-1].name,
                            listOf(people[79-2].name, people[11-1].name, people[1-1].name, people[10-1].name)

                        )

                        val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionMc(newArgs)
                        findNavController().navigate(action)

                    }
                }
            }
            else {
                // If no answer is selected, show the correct answer
                Log.d("QuizQuestionMCFragment", "No answer selected")
                showCorrect = true
                when (args.questionContents.correctAnswer) {
                    tvChoice1.text.toString() -> tvChoice1.setBackgroundColor(greenColor)
                    tvChoice2.text.toString() -> tvChoice2.setBackgroundColor(greenColor)
                    tvChoice3.text.toString() -> tvChoice3.setBackgroundColor(greenColor)
                    tvChoice4.text.toString() -> tvChoice4.setBackgroundColor(greenColor)
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()

        characterDetailsViewModel.loadSWAPICharactersDetails(randomNumber)

//        characterDetails?.homeworldId?.let { planetDetailsViewModel.loadSWAPIPlanetDetails(it) }
        planetDetailsViewModel.loadSWAPIPlanetDetails(1)
    }

    private fun generateRandomNumber(range: IntRange): Int {
        var randomNumber: Int
        do {
            randomNumber = Random.nextInt(range.first, range.last + 1)
        } while (randomNumber == 17)
        return randomNumber
    }
}


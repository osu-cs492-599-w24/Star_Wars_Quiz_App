package com.example.starwarsquiz.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.CharacterDetails
import com.example.starwarsquiz.data.PlanetDetails
import com.example.starwarsquiz.data.QuestionContents
import com.example.starwarsquiz.data.SWAPICharacter
import kotlin.random.Random

class QuizQuestionFRFragment : Fragment(R.layout.fragment_quiz_question_fr) {
    private val args: QuizQuestionFRFragmentArgs by navArgs()

    // declare necessary view models here
    private val quizScoreViewModel: QuizScoreViewModel by viewModels()
    private val characterListViewModel: SWAPICharacterViewModel by viewModels()
    private val characterDetailsViewModel: SWAPICharacterDetailsViewModel by viewModels()
    private val planetDetailsViewModel: SWAPIPlanetDetailsViewModel by viewModels()

    private lateinit var questionNumTV: TextView
    private lateinit var currentScoreTV: TextView
    private lateinit var questionTV: TextView
    private lateinit var answerET: EditText
    private lateinit var submitButton: Button
    private lateinit var nextButton: Button

    private var characterList: List<SWAPICharacter>? = null
    private var characterDetails: CharacterDetails? = null
    private var planetDetails: PlanetDetails? = null
    private var listSize = 1..50
    private val randomNumber = generateRandomNumber(listSize, 17)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionNumTV = view.findViewById(R.id.tv_quiz_question_num)
        currentScoreTV = view.findViewById(R.id.tv_quiz_current_score)
        questionTV = view.findViewById(R.id.tv_quiz_question)
        answerET = view.findViewById(R.id.et_answer_box)
        submitButton = view.findViewById(R.id.button_submit)
        nextButton = view.findViewById(R.id.button_next)

        /*
            perform logic below

            eg. when submit is clicked, create snackbar indicating if answer was correct,
            then turn button invisible, and set 'next' button to visible to navigate to next question
            (or results screen if last question)
         */

        // If this is not the final question, show the next button
        if (args.questionContents.quizNumber < 10) {
            nextButton.visibility = View.VISIBLE
            submitButton.visibility = View.INVISIBLE
        }

        // Set question number
        questionNumTV.text = args.questionContents.quizNumber.toString()

        // Set question
        questionTV.text = args.questionContents.question

        // Set current score
        currentScoreTV.text = args.questionContents.currentScore.toString()

        val oldNextVisibility = nextButton.visibility
        val oldSubmitVisibility = submitButton.visibility
        nextButton.visibility = View.INVISIBLE
        submitButton.visibility = View.INVISIBLE
        characterDetailsViewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (!loading) {
                planetDetailsViewModel.loading.observe(viewLifecycleOwner) { loading ->
                    if (!loading) {
                        nextButton.visibility = oldNextVisibility
                        submitButton.visibility = oldSubmitVisibility
                    }
                }
            }
        }

        // Submit button goes to results screen
        submitButton.setOnClickListener {
            val score = if (answerET.text.toString()
                    .equals(args.questionContents.correctAnswer, ignoreCase = true)
            ) {
                // If answer is correct, increment score
                args.questionContents.currentScore + 1
            } else {
                // If answer is incorrect, do not increment score
                args.questionContents.currentScore
            }
            val action = QuizQuestionFRFragmentDirections.navigateToQuizResults(score)
            findNavController().navigate(action)
        }

        // Next button goes to next question
        nextButton.setOnClickListener {
            // Create new args to pass to next fragment
            // Args is the QuestionContents object with the updated score

            Log.d("QuizQuestionFRFragment", "Next button clicked")

            // New score
            val nextScore = if (answerET.text.toString()
                    .equals(args.questionContents.correctAnswer, ignoreCase = true)
            ) {
                // If answer is correct, increment score
                args.questionContents.currentScore + 1
            } else {
                // If answer is incorrect, do not increment score
                args.questionContents.currentScore
            }

            val newArgs = QuestionContents(
                args.questionContents.quizNumber + 1,
                nextScore,
                "REPLACE ME WITH AN ACTUAL QUESTION",
                "ANSWER 1",
                listOf("ANSWER 1", "ANSWER 2", "ANSWER 3", "ANSWER 4")
            )
            val action = QuizQuestionFRFragmentDirections.navigateToQuizQuestionMc(newArgs)
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
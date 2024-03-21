package com.example.starwarsquiz.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.QuestionContents
import com.google.android.material.card.MaterialCardView

class QuizQuestionMCFragment : Fragment(R.layout.fragment_quiz_question_mc){
    private val args: QuizQuestionMCFragmentArgs by navArgs()

    // declare necessary view models here
    private val quizScoreViewModel: QuizScoreViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var chosenAnswer = ""
        var showCorrect = false

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

        // Set answer choice click listeners
        // Color it yellow, uncolor the others
        // Yes, getColor is deprecated. I don't care.
        mcChoice1.setOnClickListener {
            if (!showCorrect) {
                tvChoice1.setBackgroundColor(resources.getColor(R.color.sw_yellow))
                tvChoice2.setBackgroundColor(resources.getColor(R.color.grey))
                tvChoice3.setBackgroundColor(resources.getColor(R.color.grey))
                tvChoice4.setBackgroundColor(resources.getColor(R.color.grey))
                chosenAnswer = tvChoice1.text.toString()
            }
        }
        mcChoice2.setOnClickListener {
            if (!showCorrect) {
                tvChoice2.setBackgroundColor(resources.getColor(R.color.sw_yellow))
                tvChoice1.setBackgroundColor(resources.getColor(R.color.grey))
                tvChoice3.setBackgroundColor(resources.getColor(R.color.grey))
                tvChoice4.setBackgroundColor(resources.getColor(R.color.grey))
                chosenAnswer = tvChoice2.text.toString()
            }
        }
        mcChoice3.setOnClickListener {
            if (!showCorrect) {
                tvChoice3.setBackgroundColor(resources.getColor(R.color.sw_yellow))
                tvChoice1.setBackgroundColor(resources.getColor(R.color.grey))
                tvChoice2.setBackgroundColor(resources.getColor(R.color.grey))
                tvChoice4.setBackgroundColor(resources.getColor(R.color.grey))
                chosenAnswer = tvChoice3.text.toString()
            }
        }
        mcChoice4.setOnClickListener {
            if (!showCorrect) {
                tvChoice4.setBackgroundColor(resources.getColor(R.color.sw_yellow))
                tvChoice1.setBackgroundColor(resources.getColor(R.color.grey))
                tvChoice2.setBackgroundColor(resources.getColor(R.color.grey))
                tvChoice3.setBackgroundColor(resources.getColor(R.color.grey))
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
                    tvChoice1.text.toString() -> tvChoice1.setBackgroundColor(resources.getColor(R.color.sw_green))
                    tvChoice2.text.toString() -> tvChoice2.setBackgroundColor(resources.getColor(R.color.sw_green))
                    tvChoice3.text.toString() -> tvChoice3.setBackgroundColor(resources.getColor(R.color.sw_green))
                    tvChoice4.text.toString() -> tvChoice4.setBackgroundColor(resources.getColor(R.color.sw_green))
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

                val newArgs = QuestionContents(
                    args.questionContents.quizNumber + 1,
                    nextScore,
                    "REPLACE ME WITH AN ACTUAL QUESTION",
                    "ANSWER 1",
                    listOf("ANSWER 1", "ANSWER 2", "ANSWER 3", "ANSWER 4")
                )

                val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                findNavController().navigate(action)
            }
            else {
                // If no answer is selected, show the correct answer
                Log.d("QuizQuestionMCFragment", "No answer selected")
                showCorrect = true
                when (args.questionContents.correctAnswer) {
                    tvChoice1.text.toString() -> tvChoice1.setBackgroundColor(resources.getColor(R.color.sw_green))
                    tvChoice2.text.toString() -> tvChoice2.setBackgroundColor(resources.getColor(R.color.sw_green))
                    tvChoice3.text.toString() -> tvChoice3.setBackgroundColor(resources.getColor(R.color.sw_green))
                    tvChoice4.text.toString() -> tvChoice4.setBackgroundColor(resources.getColor(R.color.sw_green))
                }
            }
        }


    }
}
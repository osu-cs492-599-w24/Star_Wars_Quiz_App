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
import com.example.starwarsquiz.data.QuestionContents

class QuizQuestionFRFragment : Fragment(R.layout.fragment_quiz_question_fr) {
    private val args: QuizQuestionFRFragmentArgs by navArgs()

    // declare necessary view models here
    private val quizScoreViewModel: QuizScoreViewModel by viewModels()

    private lateinit var questionNumTV: TextView
    private lateinit var currentScoreTV: TextView
    private lateinit var questionTV: TextView
    private lateinit var answerET: EditText
    private lateinit var submitButton: Button
    private lateinit var nextButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionNumTV = view.findViewById(R.id.tv_quiz_question_num)
        currentScoreTV = view.findViewById(R.id.tv_quiz_current_score)
        questionTV = view.findViewById(R.id.tv_quiz_question)
        answerET = view.findViewById(R.id.et_answer_box)
        submitButton = view.findViewById(R.id.button_submit)
        nextButton = view.findViewById(R.id.button_next)

        var showCorrect = false

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

        // Submit button goes to results screen
        submitButton.setOnClickListener {
            if (showCorrect) {
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
            } else {
                // Lock the entry box and show the correct answer
                // If it's correct, color it sw_green, if not, color it sw_red
                answerET.isEnabled = false
                answerET.setText(args.questionContents.correctAnswer)

                answerET.setBackgroundResource(
                    if (answerET.text.toString()
                            .equals(args.questionContents.correctAnswer, ignoreCase = true)
                    ) {
                        R.color.sw_green
                    } else {
                        R.color.sw_red
                    }
                )

                showCorrect = true
            }
        }

        // Next button goes to next question
        nextButton.setOnClickListener {
            // Create new args to pass to next fragment
            // Args is the QuestionContents object with the updated score
            if (showCorrect) {
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
            } else {
                // Lock the entry box and show the correct answer
                // If it's correct, color it sw_green, if not, color it sw_red
                answerET.isEnabled = false
                answerET.setText(args.questionContents.correctAnswer)

                answerET.setBackgroundResource(
                    if (answerET.text.toString()
                            .equals(args.questionContents.correctAnswer, ignoreCase = true)
                    ) {
                        R.color.sw_green
                    } else {
                        R.color.sw_red
                    }
                )

                showCorrect = true
            }
        }
    }
}
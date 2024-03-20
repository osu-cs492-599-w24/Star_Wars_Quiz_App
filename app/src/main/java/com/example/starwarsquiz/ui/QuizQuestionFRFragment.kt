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


            /*
            val newArgs = QuestionContents(
                args.questionContents.quizNumber + 1,
                nextScore,
                "REPLACE ME WITH AN ACTUAL QUESTION",
                "ANSWER 1",
                listOf("ANSWER 1", "ANSWER 2", "ANSWER 3", "ANSWER 4")
            )
            */
            
            //58-1 or -2 bc of people/17?
            args.questionContents.quizNumber + 1
            
            if(args.questionContents.quizNumber == 1){
            
            	val newArgs = QuestionContents(
                            args.questionContents.quizNumber,
                            nextScore,
                            "What planet is Darth Maul from?",
                            SWAPIPlanetList[36-1].name,
                            listOf(SWAPIPlanetList[1-1].name, SWAPIPlanetList[36-1].name, SWAPIPlanetList[14-1].name, SWAPIPlanetList[8-1].name)
                        )
            
            }
            
            else if(args.questionContents.quizNumber == 2){
            
                val newArgs = QuestionContents(
                            args.questionContents.quizNumber,
                            nextScore,
                            "Who was Starkiller's master?",
                            SWAPICharacterList[4-1].name,
                            listOf(SWAPICharacterList[4-1].name, SWAPICharacterList[11-1].name, SWAPICharacterList[10-1].name, SWAPICharacterList[4-1].name)
            
                        )
            
            }
            
            else if(args.questionContents.quizNumber == 3){
            
                val newArgs = QuestionContents(
                            args.questionContents.quizNumber,
                            nextScore,
                            "Who was the jedi that discovered Ahsoka?", 
                            SWAPICharacterList[58-1].name,
                            listOf(SWAPICharacterList[11-1].name, SWAPICharacterList[53-1].name, SWAPICharacterList[10-1].name, SWAPICharacterList[58-1].name)
            
                        )
            
            }
            
            else if(args.questionContents.quizNumber == 4){
            
                val newArgs = QuestionContents(
                            args.questionContents.quizNumber,
                            nextScore,
                            "Whose DNA was used to create the clone troopers?",
                            SWAPICharacterList[69-1].name,
                            listOf(SWAPICharacterList[69-1].name, SWAPICharacterList[72-1].name, SWAPICharacterList[22-1].name, SWAPICharacterList[67-1].name)
            
                        )
            
            }
            
            else if(args.questionContents.quizNumber == 5){
            
                val newArgs = QuestionContents(
                            args.questionContents.quizNumber,
                            nextScore,
                            "Who was the only unaltered clone?",
                            SWAPICharacterList[22-1].name,
                            listOf(SWAPICharacterList[69-1].name, SWAPICharacterList[72-1].name, SWAPICharacterList[22-1].name, SWAPICharacterList[67-1].name)
            
                        )
            
            }
            
            else if(args.questionContents.quizNumber == 6){
            
                val newArgs = QuestionContents(
                            args.questionContents.quizNumber,
                            nextScore,
                            "Who was Qui Gon Jinn's master?"
                            SWAPICharacterList[67-1].name,
                            listOf(SWAPICharacterList[1-1].name, SWAPICharacterList[20-1].name, SWAPICharacterList[67-1].name, SWAPICharacterList[10-1].name)
            
                        )
            
            }
            
            else if(args.questionContents.quizNumber == 7){
            
                val newArgs = QuestionContents(
                            args.questionContents.quizNumber,
                            nextScore,
                            "What planet was Palpatine from?"
                            SWAPIPlanetList[8-1].name,
                            listOf(SWAPIPlanetList[1-1].name, SWAPIPlanetList[9-1].name, SWAPIPlanetList[7-1].name, SWAPIPlanetList[8-1].name)
            
                        )
            
            }
            
            else if(args.questionContents.quizNumber == 8){
            
                val newArgs = QuestionContents(
                            args.questionContents.quizNumber,
                            nextScore,
                            "What planet was Starkiller from?"
                            SWAPIPlanetList[14-1].name,
                            listOf(SWAPIPlanetList[9-1].name, SWAPIPlanetList[14-1].name, SWAPIPlanetList[10-1].name, SWAPIPlanetList[5-1].name)
            
                        )
            
            }
            
            else if(args.questionContents.quizNumber == 9){
            
                val newArgs = QuestionContents(
                            args.questionContents.quizNumber,
                            nextScore,
                            "Who inquired about the droid attack on the wookies?"
                            SWAPICharacterList[52-1].name,
                            listOf(SWAPICharacterList[52-1].name, SWAPICharacterList[57-1].name, SWAPICharacterList[58-1].name, SWAPICharacterList[51-1].name)
            
                        )
            
            }
            
            else if(args.questionContents.quizNumber == 10){
            
                val newArgs = QuestionContents(
                            args.questionContents.quizNumber,
                            nextScore,
                            "Hello there"
                            SWAPICharacterList[10-1].name,
                            listOf(SWAPICharacterList[79-1].name, SWAPICharacterList[11-1].name, SWAPICharacterList[1-1].name, SWAPICharacterList[10-1].name)
            
                        )
            
            }
            
            val action = QuizQuestionFRFragmentDirections.navigateToQuizQuestionMc(newArgs)
            findNavController().navigate(action)
        }
    }
}

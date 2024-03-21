package com.example.starwarsquiz.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.QuestionContents
import com.example.starwarsquiz.data.SWAPICharacter
import com.example.starwarsquiz.data.SWAPIPlanet

class QuizQuestionMCFragment : Fragment(R.layout.fragment_quiz_question_mc){
    private val args: QuizQuestionMCFragmentArgs by navArgs()

    // declare necessary view models here
    private val quizScoreViewModel: QuizScoreViewModel by viewModels()

    private val planetsViewModel: SWAPIPlanetViewModel by viewModels()
    private val resultViewModel: SWAPICharacterViewModel by viewModels()

    // stores list of possible answers to this question
    private val adapter = MCAnswerListAdapter()

    private lateinit var questionNumTV: TextView
    private lateinit var currentScoreTV: TextView
    private lateinit var questionTV: TextView
    private lateinit var answerListRV: RecyclerView
    private lateinit var submitButton: Button
    private lateinit var nextButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        planetsViewModel.loadSWAPIPlanets(1, 40)
        var planet = listOf<SWAPIPlanet>()

        resultViewModel.loadSWAPICharacters(1, 80)
        var people = listOf<SWAPICharacter>()

        questionNumTV = view.findViewById(R.id.tv_quiz_question_num)
        currentScoreTV = view.findViewById(R.id.tv_quiz_current_score)
        questionTV = view.findViewById(R.id.tv_quiz_question)
        answerListRV = view.findViewById(R.id .rv_quiz_question_answers)
        submitButton = view.findViewById(R.id.button_submit)
        nextButton = view.findViewById(R.id.button_next)

        answerListRV.adapter = adapter
        answerListRV.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

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
        adapter.updateAnswerList(args.questionContents.answerChoices)


        // Submit button goes to results screen
        submitButton.setOnClickListener {
            val score = if (true) { // TODO: Replace with actual answer comparison
                // If answer is correct, increment score
                args.questionContents.currentScore + 1
            } else {
                // If answer is incorrect, do not increment score
                args.questionContents.currentScore
            }
            val action = QuizQuestionMCFragmentDirections.navigateToQuizResults(score)
            findNavController().navigate(action)
        }

        // Next button goes to next question
        nextButton.setOnClickListener {

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

            val nextScore = if (true) { // TODO: Replace with actual answer comparison
                // If answer is correct, increment score
                args.questionContents.currentScore + 1
            } else {
                // If answer is incorrect, do not increment score
                args.questionContents.currentScore
            }



            //58-1 or -2 bc of people/17?

            val nextQ : Int = args.questionContents.quizNumber + 1

            val newArgs : QuestionContents

            when (nextQ) {
                1 -> {

                    newArgs = QuestionContents(
                        args.questionContents.quizNumber,
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
                        args.questionContents.quizNumber,
                        nextScore,
                        "Who was Starkiller's master?",
                        people[4-1].name,
                        listOf(people[4-1].name, people[11-1].name, people[10-1].name, people[4-1].name)

                    )

                    val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                    findNavController().navigate(action)

                }
                3 -> {

                    newArgs = QuestionContents(
                        args.questionContents.quizNumber,
                        nextScore,
                        "Who was the jedi that discovered Ahsoka?",
                        people[58-1].name,
                        listOf(people[11-1].name, people[53-1].name, people[10-1].name, people[58-1].name)

                    )

                    val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                    findNavController().navigate(action)

                }
                4 -> {

                    newArgs = QuestionContents(
                        args.questionContents.quizNumber,
                        nextScore,
                        "Whose DNA was used to create the clone troopers?",
                        people[69-1].name,
                        listOf(people[69-1].name, people[72-1].name, people[22-1].name, people[67-1].name)

                    )

                    val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                    findNavController().navigate(action)

                }
                5 -> {

                    newArgs = QuestionContents(
                        args.questionContents.quizNumber,
                        nextScore,
                        "Who was the only unaltered clone?",
                        people[22-1].name,
                        listOf(people[69-1].name, people[72-1].name, people[22-1].name, people[67-1].name)

                    )

                    val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                    findNavController().navigate(action)

                }
                6 -> {

                    newArgs = QuestionContents(
                        args.questionContents.quizNumber,
                        nextScore,
                        "Who was Qui Gon Jinn's master?",
                        people[67-1].name,
                        listOf(people[1-1].name, people[20-1].name, people[67-1].name, people[10-1].name)

                    )

                    val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                    findNavController().navigate(action)

                }
                7 -> {

                    newArgs = QuestionContents(
                        args.questionContents.quizNumber,
                        nextScore,
                        "What planet was Palpatine from?",
                        planet[8-1].name,
                        listOf(planet[1-1].name, planet[9-1].name, planet[7-1].name, planet[8-1].name)

                    )

                    val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                    findNavController().navigate(action)

                }
                8 -> {

                    newArgs = QuestionContents(
                        args.questionContents.quizNumber,
                        nextScore,
                        "What planet was Starkiller from?",
                        planet[14-1].name,
                        listOf(planet[9-1].name, planet[14-1].name, planet[10-1].name, planet[5-1].name)

                    )

                    val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                    findNavController().navigate(action)

                }
                9 -> {

                    newArgs = QuestionContents(
                        args.questionContents.quizNumber,
                        nextScore,
                        "Who inquired about the droid attack on the wookies?",
                        people[52-1].name,
                        listOf(people[52-1].name, people[57-1].name, people[58-1].name, people[51-1].name)

                    )

                    val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                    findNavController().navigate(action)

                }
                10 -> {

                    newArgs = QuestionContents(
                        args.questionContents.quizNumber,
                        nextScore,
                        "Hello there",
                        people[10-1].name,
                        listOf(people[79-1].name, people[11-1].name, people[1-1].name, people[10-1].name)

                    )

                    val action = QuizQuestionMCFragmentDirections.navigateToQuizQuestionFr(newArgs)
                    findNavController().navigate(action)

                }
            }

        }


    }
}
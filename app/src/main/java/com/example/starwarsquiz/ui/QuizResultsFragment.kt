package com.example.starwarsquiz.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.starwarsquiz.R

class QuizResultsFragment : Fragment(R.layout.fragment_quiz_results) {
    // declare necessary view models here
    private val quizScoreViewModel: QuizScoreViewModel by viewModels()

    private lateinit var finalScoreTV: TextView
    private lateinit var highestScoreTV: TextView
    private lateinit var homeButton: Button
    private lateinit var restartButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finalScoreTV = view.findViewById(R.id.tv_final_score)
        highestScoreTV = view.findViewById(R.id.tv_highest_score)
        homeButton = view.findViewById(R.id.button_home)
        restartButton = view.findViewById(R.id.button_restart_quiz)

        /*
            perform logic below
            eg. navigate to home on home btn click or first question on restart btn click
         */
    }
}
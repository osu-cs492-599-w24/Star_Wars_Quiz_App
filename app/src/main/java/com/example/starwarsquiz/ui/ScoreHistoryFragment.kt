package com.example.starwarsquiz.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsquiz.R

class ScoreHistoryFragment : Fragment(R.layout.fragment_score_history) {
    private val quizScoreViewModel: QuizScoreViewModel by viewModels()

    private lateinit var scoresRV: RecyclerView
    private lateinit var homeButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeButton = view.findViewById(R.id.button_home)

        scoresRV = view.findViewById(R.id.rv_scores)
        scoresRV.layoutManager = LinearLayoutManager(requireContext())
        scoresRV.setHasFixedSize(true)

        // Home button goes to landing page
        homeButton.setOnClickListener {
            val action = ScoreHistoryFragmentDirections.navigateToLandingPage()
            findNavController().navigate(action)
        }
    }

}
package com.example.starwarsquiz.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsquiz.R

class ScoreHistoryFragment : Fragment(R.layout.fragment_score_history) {
    private val quizScoreViewModel: QuizScoreViewModel by viewModels()

    private lateinit var scoresRV: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scoresRV = view.findViewById(R.id.rv_scores)
        scoresRV.layoutManager = LinearLayoutManager(requireContext())
        scoresRV.setHasFixedSize(true)
    }

}
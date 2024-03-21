package com.example.starwarsquiz.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.QuizScoreEntity

class ScoreHistoryFragment : Fragment(R.layout.fragment_score_history) {
    private val adapter = ScoreListAdapter(::onScoreClick)
    private val viewModel: QuizScoreViewModel by viewModels()

    private lateinit var scoresRV: RecyclerView
    private lateinit var clearHistoryButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clearHistoryButton = view.findViewById(R.id.button_clear_history)

        val bottomNavBar = requireActivity().findViewById<View>(R.id.bottom_nav)
        bottomNavBar.visibility = View.VISIBLE

        scoresRV = view.findViewById(R.id.rv_scores)
        scoresRV.layoutManager = LinearLayoutManager(requireContext())
        scoresRV.setHasFixedSize(true)
        scoresRV.adapter = adapter

        viewModel.allQuizScores.observe(viewLifecycleOwner) { allQuizScores ->
            adapter.updateScoreList(allQuizScores)
        }

        clearHistoryButton.setOnClickListener {
            viewModel.clearExceptHighestScores()
        }
    }

    private fun onScoreClick(score: QuizScoreEntity) {
        val shareText = getString(
            R.string.share_text,
            score.score.toString()
        )
        val intent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))
    }

}
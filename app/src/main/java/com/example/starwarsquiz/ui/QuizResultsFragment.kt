package com.example.starwarsquiz.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.starwarsquiz.R

class QuizResultsFragment : Fragment(R.layout.fragment_quiz_results) {
    private val args: QuizResultsFragmentArgs by navArgs()

    private val scoreViewModel by viewModels<QuizScoreViewModel>()

    private lateinit var rewardVV: VideoView
    private lateinit var scoreTV: TextView
    private lateinit var highscoreTV: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rewardVV = view.findViewById(R.id.vv_reward_video)
        scoreTV = view.findViewById(R.id.tv_final_score)
        highscoreTV = view.findViewById(R.id.tv_highest_score)

        scoreViewModel.highestScore.observe(viewLifecycleOwner) { highScore ->
            highscoreTV.text = getString(R.string.highest_score, highScore.toString())
        }

        // Set the score text
        val score = args.quizResults
        scoreTV.text = getString(R.string.your_score, score.toString())

        // Tap-to-pause video
        rewardVV.setOnClickListener {
            if (rewardVV.isPlaying) {
                rewardVV.pause()
            } else {
                rewardVV.start()
            }
        }

        // If we're passed the quiz results, we can play the reward video
        val videoPath = getRewardVideo(score)

        rewardVV.setVideoPath(videoPath)

        rewardVV.start()
    }

    private fun getRewardVideo(score: Int) : String {
        val videoResource = when (score) {
            in 0..1 -> R.raw.deathstar
            in 2..3 -> R.raw.father
            in 4..5 -> R.raw.order66
            in 6..7 -> R.raw.noconfidence
            else -> R.raw.highground
        }
        return "android.resource://" + requireActivity().packageName + "/" + videoResource
    }
}
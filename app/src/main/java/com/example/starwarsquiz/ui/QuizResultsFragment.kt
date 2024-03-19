package com.example.starwarsquiz.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.starwarsquiz.R

class QuizResultsFragment : Fragment(R.layout.fragment_quiz_results) {
    private val args: QuizResultsFragmentArgs by navArgs()

    // declare necessary view models here
    private val quizScoreViewModel: QuizScoreViewModel by viewModels()

    private lateinit var finalScoreTV: TextView
    private lateinit var highestScoreTV: TextView
    private lateinit var homeButton: Button
    private lateinit var restartButton: Button
    private lateinit var shareButton: ImageButton
    private lateinit var rewardVV: VideoView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finalScoreTV = view.findViewById(R.id.tv_final_score)
        highestScoreTV = view.findViewById(R.id.tv_highest_score)
        homeButton = view.findViewById(R.id.button_home)
        restartButton = view.findViewById(R.id.button_restart_quiz)
        shareButton = view.findViewById(R.id.button_share_score)
        rewardVV = view.findViewById(R.id.vv_reward_video)

        /*
            perform logic below
            eg. navigate to home on home btn click or first question on restart btn click
         */

        // Restart button goes to first question
        restartButton.setOnClickListener {
            val action = QuizResultsFragmentDirections.navigateToQuizQuestionMc()
            findNavController().navigate(action)
        }

        // Home button goes to landing page
        homeButton.setOnClickListener {
            val action = QuizResultsFragmentDirections.navigateToLandingPage()
            findNavController().navigate(action)
        }

        shareButton.setOnClickListener {
            val shareText = getString(
                R.string.share_text,
                args.quizResults.toString()
            )
            val intent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,shareText)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, null))
        }

        quizScoreViewModel.highestScore.observe(viewLifecycleOwner) { highScore ->
            highestScoreTV.text = getString(R.string.highest_score, highScore.toString())
        }

        // Set the score text
        val score = args.quizResults
        finalScoreTV.text = getString(R.string.your_score, score.toString())

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

    private fun getRewardVideo(score: Int): String {
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
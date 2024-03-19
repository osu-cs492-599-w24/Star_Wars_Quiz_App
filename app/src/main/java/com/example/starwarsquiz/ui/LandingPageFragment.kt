package com.example.starwarsquiz.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.starwarsquiz.R

class LandingPageFragment : Fragment(R.layout.fragment_landing_page){
    private lateinit var startButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startButton = view.findViewById(R.id.start_button)

        // navigate to first question on start btn click
        startButton.setOnClickListener {
            val action = LandingPageFragmentDirections.navigateToQuizQuestionMc()
            findNavController().navigate(action)
        }
    }
}
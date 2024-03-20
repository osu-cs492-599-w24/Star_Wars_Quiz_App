package com.example.starwarsquiz.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.QuestionContents

class LandingPageFragment : Fragment(R.layout.fragment_landing_page){
    private lateinit var startButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startButton = view.findViewById(R.id.start_button)

        // navigate to first question on start btn click
        startButton.setOnClickListener {
            val newArgs = QuestionContents(
                1,
                0,
                "What planet is Darth Maul from?",
                SWAPIPlanetList[36-1].name,
                listOf(SWAPIPlanetList[1-1].name, SWAPIPlanetList[36-1].name, SWAPIPlanetList[14-1].name, SWAPIPlanetList[8-1].name)
            )
            val action = LandingPageFragmentDirections.navigateToQuizQuestionMc(newArgs)
            findNavController().navigate(action)
        }
    }
}

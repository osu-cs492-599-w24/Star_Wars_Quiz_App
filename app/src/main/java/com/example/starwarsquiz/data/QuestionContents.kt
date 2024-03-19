package com.example.starwarsquiz.data

import java.io.Serializable

data class QuestionContents(
    val quizNumber: Int,
    val question: String,
    val correctAnswer: String,
    val answerChoices: List<String>?
) : Serializable

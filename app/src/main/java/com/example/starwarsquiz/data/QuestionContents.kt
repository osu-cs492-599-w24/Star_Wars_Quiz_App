package com.example.starwarsquiz.data

data class QuestionContents(
    val quizNumber: Int,
    val question: String,
    val correctAnswer: String,
    val answerChoices: List<String>?
)

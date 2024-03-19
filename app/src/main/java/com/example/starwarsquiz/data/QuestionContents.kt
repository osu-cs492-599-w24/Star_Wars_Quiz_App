package com.example.starwarsquiz.data

/*
* this object is passed as a nav arg to a question screen to dynamically render its contents
* */
data class QuestionContents(
    val quizNumber: Int,
    val question: String,
    val correctAnswer: String,
    val answerChoices: List<String>?
)

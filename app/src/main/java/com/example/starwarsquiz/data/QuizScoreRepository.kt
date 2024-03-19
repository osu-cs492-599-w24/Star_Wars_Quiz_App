package com.example.starwarsquiz.data

class QuizScoreRepository(
    private val dao: QuizScoreDao
) {
    suspend fun insertQuizScore(quizScore: QuizScoreEntity) = dao.insert(quizScore)
    suspend fun updateQuizScore(runId: Int, score: Int) = dao.updateCurrentScore(runId, score)
    fun getHighestScore() = dao.getHighestScore()
}
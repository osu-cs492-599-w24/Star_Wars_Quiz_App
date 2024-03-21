package com.example.starwarsquiz.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizScoreDao {
    @Insert
    suspend fun insert(score: QuizScoreEntity)

    @Query(
        "DELETE FROM QuizScoreEntity WHERE (score, timestamp) NOT IN (" +
                "SELECT score, MAX(timestamp) FROM QuizScoreEntity WHERE score = (" +
                "SELECT MAX(score) FROM QuizScoreEntity))"
    )
    suspend fun deleteAllExceptHighestScore()

    @Query("SELECT * FROM QuizScoreEntity ORDER BY score DESC")
    fun getAllScores() : Flow<List<QuizScoreEntity>>

    @Query("UPDATE QuizScoreEntity SET score = :newScore WHERE runId = :runId")
    suspend fun updateCurrentScore(runId: Int, newScore: Int)

    @Query("SELECT MAX(score) FROM QuizScoreEntity")
    fun getHighestScore() : Flow<Int>
}
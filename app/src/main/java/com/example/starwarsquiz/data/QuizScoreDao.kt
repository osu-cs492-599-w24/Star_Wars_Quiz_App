package com.example.starwarsquiz.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizScoreDao {
    @Insert
    suspend fun insert(score: QuizScoreEntity)

    @Query("SELECT * FROM QuizScoreEntity")
    fun getAllScores() : Flow<List<QuizScoreEntity>>

    @Query("UPDATE QuizScoreEntity SET score = :newScore WHERE runId = :runId")
    suspend fun updateCurrentScore(runId: Int, newScore: Int)

    @Query("SELECT MAX(score) FROM QuizScoreEntity")
    fun getHighestScore() : Flow<Int>
}
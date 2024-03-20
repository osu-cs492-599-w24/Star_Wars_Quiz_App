package com.example.starwarsquiz.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class QuizScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val runId: Int,
    val score: Int,
    val timestamp: Long
) : Serializable

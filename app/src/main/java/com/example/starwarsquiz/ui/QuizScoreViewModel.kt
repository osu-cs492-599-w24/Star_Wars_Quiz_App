package com.example.starwarsquiz.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.starwarsquiz.data.AppDatabase
import com.example.starwarsquiz.data.QuizScoreEntity
import com.example.starwarsquiz.data.QuizScoreRepository
import kotlinx.coroutines.launch

class QuizScoreViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = QuizScoreRepository(
        AppDatabase.getInstance(application).quizScoreDao()
    )

    val highestScore = repository.getHighestScore().asLiveData()

    val allQuizScores = repository.getAllScores().asLiveData()

    fun addQuizScore(quizScore: QuizScoreEntity) {
        viewModelScope.launch {
            repository.insertQuizScore(quizScore)
        }
    }

    fun updateQuizScore(runId: Int, newScore: Int) {
        viewModelScope.launch {
            repository.updateQuizScore(runId, newScore)
        }
    }

    fun clearExceptHighestScores() {
        viewModelScope.launch {
            repository.deleteAllExceptHighScore()
        }
    }
}
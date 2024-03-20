package com.example.starwarsquiz.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.QuizScoreEntity
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ScoreListAdapter(
    private val onScoreClick: (QuizScoreEntity) -> Unit
) : RecyclerView.Adapter<ScoreListAdapter.ScoreViewHolder>(){
    private var scoreList = listOf<QuizScoreEntity>()

    fun updateScoreList(newScoreList: List<QuizScoreEntity>?) {
        notifyItemRangeRemoved(0, scoreList.size)
        scoreList = newScoreList ?: listOf()
        notifyItemRangeInserted(0, scoreList.size)
    }

    override fun getItemCount() = scoreList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_list_item, parent, false)
        return ScoreViewHolder(itemView, onScoreClick)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(scoreList[position])
    }

    class ScoreViewHolder(
        itemView: View,
        onScoreClick: (QuizScoreEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val scoreTV: TextView = itemView.findViewById(R.id.tv_points)
        private var currentScore: QuizScoreEntity? = null

        init {
            itemView.setOnClickListener {
                currentScore?.let(onScoreClick)
            }
        }

        fun bind(quizScore: QuizScoreEntity) {
            currentScore = quizScore
            val score = quizScore.score.toString()
            val unixTime = quizScore.timestamp
            val date = Date(unixTime)
            val dayOfMonth = SimpleDateFormat("d", Locale.getDefault()).format(date).toInt()
            val daySuffix = when {
                dayOfMonth in 11..13 -> "th"
                dayOfMonth % 10 == 1 -> "st"
                dayOfMonth % 10 == 2 -> "nd"
                dayOfMonth % 10 == 3 -> "rd"
                else -> "th"
            }
            val sdf = SimpleDateFormat("MMM d'$daySuffix'", Locale.getDefault())
            val formattedDate = sdf.format(date)

            scoreTV.text = "Your Score was ${score}/10 on ${formattedDate}"
        }
    }
}
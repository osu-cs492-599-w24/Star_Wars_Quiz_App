package com.example.starwarsquiz.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsquiz.R
import com.example.starwarsquiz.data.QuizScoreEntity

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

        fun bind(score: QuizScoreEntity) {
            currentScore = score
            scoreTV.text = score.score.toString()
        }
    }
}
package com.example.starwarsquiz.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsquiz.R

class MCAnswerListAdapter() : RecyclerView.Adapter<MCAnswerListAdapter.MCAnswerViewHolder>() {
    private var answerList = listOf<String>()

    fun updateAnswerList(newAnswerList: List<String>?) {
        notifyItemRangeRemoved(0, answerList.size)
        answerList = newAnswerList ?: listOf()
        notifyItemRangeInserted(0, answerList.size)
    }

    override fun getItemCount() = answerList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MCAnswerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.mc_answer_list_item, parent, false)
        return MCAnswerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MCAnswerViewHolder, position: Int) {
        holder.bind(answerList[position])
    }

    class MCAnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val answerTV: TextView = itemView.findViewById(R.id.tv_mc_answer)
        private var currentAnswer: String? = null

        init {
            itemView.setOnClickListener {
//                itemView.setBackgroundColor(Color.parseColor("#ADD8E6"))
            }
        }

        fun bind(answer: String) {
            currentAnswer = answer
            answerTV.text = answer
        }
    }
}
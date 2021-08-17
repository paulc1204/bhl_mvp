package com.example.mvp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvp.data.entities.Problem
import com.example.mvp.databinding.ProblemListItemBinding
import java.time.format.DateTimeFormatter

class ProblemListAdapter(private val onProblemClicked: (Problem) -> Unit):
    ListAdapter<Problem, ProblemListAdapter.ProblemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder {
        return ProblemViewHolder(
            ProblemListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener{
            onProblemClicked(current)
        }
        holder.bind(current)
    }


    class ProblemViewHolder(private var binding: ProblemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(problem: Problem) {
            binding.problemTitle.text = problem.title
            binding.problemDate.text = problem.timestamp?.format(DateTimeFormatter.ofPattern("MMM-dd"))
            binding.problemCategory.text = problem.category

        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Problem>() {
            override fun areItemsTheSame(oldProblem: Problem, newProblem: Problem): Boolean {
                return oldProblem === newProblem
            }

            override fun areContentsTheSame(oldProblem: Problem, newProblem: Problem): Boolean {
                return oldProblem.title == newProblem.title
            }
        }
    }

}
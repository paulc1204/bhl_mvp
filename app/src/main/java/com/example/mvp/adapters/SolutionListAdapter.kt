package com.example.mvp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.entities.Solution
import com.example.mvp.databinding.ProblemListItemBinding
import com.example.mvp.databinding.SolutionListItemBinding
import java.time.format.DateTimeFormatter

class SolutionListAdapter(private val onSolutionClicked: (Solution) -> Unit):
    ListAdapter<Solution, SolutionListAdapter.SolutionViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolutionViewHolder {
        return SolutionViewHolder(
            SolutionListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: SolutionViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener{
            onSolutionClicked(current)
        }
        holder.bind(current)
    }


    class SolutionViewHolder(private var binding: SolutionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(solution: Solution) {
            binding.solutionTitle.text = solution.title
            binding.solutionTried.text = if(solution.solvable) "Yes" else "No"
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Solution>() {
            override fun areItemsTheSame(oldSolution: Solution, newSolution: Solution): Boolean {
                return oldSolution === newSolution
            }

            override fun areContentsTheSame(oldSolution: Solution, newSolution: Solution): Boolean {
                return oldSolution.title == newSolution.title
            }
        }
    }

}
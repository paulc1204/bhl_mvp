package com.example.mvp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvp.data.entities.Distraction
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.entities.Solution
import com.example.mvp.databinding.DistractionListItemBinding
import com.example.mvp.databinding.ProblemListItemBinding
import com.example.mvp.databinding.SolutionListItemBinding
import java.time.format.DateTimeFormatter

class DistractionListAdapter(private val onDistractionClicked: (Distraction) -> Unit):
    ListAdapter<Distraction, DistractionListAdapter.DistractionViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistractionViewHolder {
        return DistractionViewHolder(
            DistractionListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: DistractionViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener{
            onDistractionClicked(current)
        }
        holder.bind(current)
    }


    class DistractionViewHolder(private var binding: DistractionListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(distraction: Distraction) {
            binding.distractionTitle.text = distraction.title
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Distraction>() {
            override fun areItemsTheSame(olddistraction: Distraction, newdistraction: Distraction): Boolean {
                return olddistraction === newdistraction
            }

            override fun areContentsTheSame(olddistraction: Distraction, newdistraction: Distraction): Boolean {
                return olddistraction.title == newdistraction.title
            }
        }
    }

}
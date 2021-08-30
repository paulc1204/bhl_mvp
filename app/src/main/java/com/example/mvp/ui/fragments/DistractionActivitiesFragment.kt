package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp.MyApplication
import com.example.mvp.R
import com.example.mvp.adapters.DistractionListAdapter
import com.example.mvp.adapters.ProblemListAdapter
import com.example.mvp.databinding.FragmentDistractionActivitiesBinding
import com.example.mvp.databinding.FragmentProblemsBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

class DistractionActivitiesFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private var _binding: FragmentDistractionActivitiesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDistractionActivitiesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DistractionListAdapter{
            val action = DistractionActivitiesFragmentDirections.actionDistractionActivitiesFragmentToDistractionDetailFragment(it.distraction_id)
            this.findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter
        viewModel.distractions.observe(this.viewLifecycleOwner){ distractions ->
            distractions.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.addActivityButton.setOnClickListener {
            val action = DistractionActivitiesFragmentDirections.actionDistractionActivitiesFragmentToAddDistractionFragment(
                    header = getString(R.string.add_fragment_title, "Distraction")
            )
            findNavController().navigate(action)
        }

    }
}
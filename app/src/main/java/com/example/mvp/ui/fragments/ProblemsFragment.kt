package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp.MyApplication
import com.example.mvp.R
import com.example.mvp.adapters.ProblemListAdapter
import com.example.mvp.databinding.FragmentProblemsBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

class ProblemsFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private var _binding: FragmentProblemsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProblemsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProblemListAdapter{
            val action = ProblemsFragmentDirections.actionProblemsFragmentToProblemDetailFragment(it.problem_id)
            this.findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter
        viewModel.problems.observe(this.viewLifecycleOwner){ problems ->
            problems.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.addProblemButton.setOnClickListener {
            val action = ProblemsFragmentDirections.actionProblemsFragmentToAddProblemFragment(
                header = getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }

    }
}
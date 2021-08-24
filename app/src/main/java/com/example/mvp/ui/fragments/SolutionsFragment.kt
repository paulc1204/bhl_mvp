package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp.MyApplication
import com.example.mvp.R
import com.example.mvp.adapters.SolutionListAdapter
import com.example.mvp.databinding.FragmentSolutionsBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

class SolutionsFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: AskSolutionFragmentArgs by navArgs()
    private var _binding: FragmentSolutionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSolutionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SolutionListAdapter{
            val action = SolutionsFragmentDirections.actionSolutionsFragmentToSolutionDetailFragment(it.solution_id, navigationArgs.problemId)
            findNavController().navigate(action)
        }

        binding.solutionsRecyclerView.adapter = adapter

        viewModel.problemsWIthSolutions.observe(this.viewLifecycleOwner){ it ->
            it.find { it.problem.problem_id == navigationArgs.problemId }
                ?.solutions
                ?.let {
                    adapter.submitList(it)
                }
        }
        binding.solutionsRecyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.addSolutionButton.setOnClickListener {
            val action = SolutionsFragmentDirections.actionSolutionsFragmentToAddSolutionFragment(
                header = getString(R.string.add_solution_title),
                problemId = navigationArgs.problemId
            )
            this.findNavController().navigate(action)
        }

        binding.toProblemListBtn.setOnClickListener {
            val action = SolutionsFragmentDirections.actionSolutionsFragmentToProblemsFragment()
            this.findNavController().navigate(action)
        }

    }
}
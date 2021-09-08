package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp.MyApplication
import com.example.mvp.R
import com.example.mvp.adapters.SolutionListAdapter
import com.example.mvp.databinding.FragmentSolutions2Binding
import com.example.mvp.databinding.FragmentSolutionsBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SecondSolutionsFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: AskSolutionFragmentArgs by navArgs()
    private var _binding: FragmentSolutions2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSolutions2Binding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun displayInstructions(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.instructions))
            .setMessage(getString(R.string.second_stage_instruction))
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SolutionListAdapter{
            val action = SecondSolutionsFragmentDirections.actionSecondSolutionsFragmentToSolutionDetailFragment(
                it.solution_id,
                navigationArgs.problemId,
                true)
            findNavController().navigate(action)
        }

        binding.solutionsRecyclerView.adapter = adapter

        viewModel.problemsWIthSolutions.observe(this.viewLifecycleOwner){ problemsWithSolutions ->
            problemsWithSolutions.find { it.problem.problem_id == navigationArgs.problemId }
                ?.solutions
                ?.let {
                    adapter.submitList(it)
                }
        }
        binding.solutionsRecyclerView.layoutManager = LinearLayoutManager(this.context)

        viewModel.retrieveProblem(navigationArgs.problemId).observe(this.viewLifecycleOwner){ selectedProblem ->
            binding.problemTitle.text = selectedProblem.title
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_instructions, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.solutionInstructionsFragment){
            displayInstructions()
            true
        }else {
            super.onOptionsItemSelected(item)
        }
    }

}
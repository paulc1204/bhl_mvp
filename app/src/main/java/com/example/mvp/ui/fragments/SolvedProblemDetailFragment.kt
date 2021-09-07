package com.example.mvp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvp.MyApplication
import com.example.mvp.R
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.entities.Solution
import com.example.mvp.databinding.FragmentSolvedProbDetailBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.format.DateTimeFormatter

class SolvedProblemDetailFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: SolvedProblemDetailFragmentArgs by navArgs()

    lateinit var problem: Problem
    lateinit var solution: Solution

    private var _binding: FragmentSolvedProbDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSolvedProbDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun bind(){
        binding.apply{
            problemTitle.text = problem.title
            problemDate.text = problem.timestamp?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a"))
            problemCategory.text = problem.category
            problemDescription.text = problem.description

            reflectionContent.text = problem.reflection
        }
        viewModel.problemsWIthSolutions.observe(this.viewLifecycleOwner) { problemWithSolutions ->
            solution = problemWithSolutions.find { it.problem.problem_id == problem.problem_id }
                    ?.solutions?.get(0)!!
            bindSolution()
        }
    }

    private fun bindSolution(){
        binding.apply {
            solutionTitle.text = solution.title
            solutionDescription.text = solution.description
        }
    }

    private fun confirmDelete(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_confirmation))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteProblemAndSolutions()
            }
            .show()
    }

    private fun deleteProblemAndSolutions(){
        viewModel.deleteProblem(problem)
        viewModel.deleteSolutions(problem.problem_id)
        findNavController().navigateUp()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.retrieveProblem(navigationArgs.problemId).observe(this.viewLifecycleOwner) { selectedProblem ->
            problem = selectedProblem
            bind()
        }

//        viewModel.problemsWIthSolutions.observe(this.viewLifecycleOwner) { problemWithSolutions ->
//            solution = problemWithSolutions.find { it.problem.problem_id == problem.problem_id }
//                    ?.solutions?.get(0)!!
//            bindSolution()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.edit -> {
                val action = SolvedProblemDetailFragmentDirections.actionSolvedProblemDetailFragmentToAddProblemFragment(
                    problemId = navigationArgs.problemId,
                    header = getString(R.string.edit_fragment_title, "Problem")
                )
                findNavController().navigate(action)
                true
            }
            R.id.delete -> {
                confirmDelete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
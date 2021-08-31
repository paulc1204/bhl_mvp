package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvp.MyApplication
import com.example.mvp.R
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.entities.Solution
import com.example.mvp.databinding.FragmentProblemDetailBinding
import com.example.mvp.databinding.FragmentSolutionDetailBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.format.DateTimeFormatter

class SolutionDetailFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: SolutionDetailFragmentArgs by navArgs()

    lateinit var solution: Solution

    private var _binding: FragmentSolutionDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSolutionDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun bind(){
        binding.apply{
            solutionTitle.text = solution.title
            solutionDescription.text = solution.description

            if(!solution.pros.isNullOrBlank() || !solution.cons.isNullOrBlank()){
                evalGroup.visibility = View.VISIBLE
                prosContent.text = solution.pros
                consContent.text = solution.cons
            }

            btnEval.setOnClickListener {
                val action = SolutionDetailFragmentDirections.actionSolutionDetailFragmentToEvalSolutionFragment(solution.solution_id)
                findNavController().navigate(action)
            }
        }
    }

    private fun confirmDelete(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_confirmation))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteSolution()
            }
            .show()
    }

    private fun deleteSolution(){
        viewModel.deleteSolution(solution)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.retrieveSolution(navigationArgs.solutionId).observe(this.viewLifecycleOwner) { selectedSolution ->
            solution = selectedSolution
            bind()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.edit -> {
                val action = SolutionDetailFragmentDirections.actionSolutionDetailFragmentToAddSolutionFragment(
                    header = getString(R.string.edit_fragment_title, "Solution"),
                    problemId = navigationArgs.problemId,
                    solutionId = solution.solution_id
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
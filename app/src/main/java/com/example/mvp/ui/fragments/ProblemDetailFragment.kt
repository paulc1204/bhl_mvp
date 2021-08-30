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
import com.example.mvp.databinding.FragmentProblemDetailBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.format.DateTimeFormatter

class ProblemDetailFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: ProblemDetailFragmentArgs by navArgs()

    lateinit var problem: Problem

    private var _binding: FragmentProblemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProblemDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun bind(problem: Problem){
        binding.apply{
            problemTitle.text = problem.title
            problemDate.text = problem.timestamp?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a"))
            problemCategory.text = problem.category
            problemDescription.text = problem.description
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
            bind(problem)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.edit -> {
                val action = ProblemDetailFragmentDirections.actionProblemDetailFragmentToAddProblemFragment(
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
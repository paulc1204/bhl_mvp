package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvp.MyApplication
import com.example.mvp.R
import com.example.mvp.data.entities.Solution
import com.example.mvp.databinding.FragmentAddSolutionBinding
import com.example.mvp.databinding.FragmentReflectionBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ReflectionFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: ReflectionFragmentArgs by navArgs()

    lateinit var solution: Solution

    private var _binding: FragmentReflectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReflectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun save(){
        val btnId = binding.yesOrNo.checkedRadioButtonId
        val solvable = binding.yesOrNo.findViewById<RadioButton>(btnId).text.toString() == "Yes"

        if(solvable){
            confirmFinalSolution()
        }else{
            onNotDoable()
        }
    }

    private fun onNotDoable(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.final_solution_not_doable))
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                deleteOtherSolutions()
                viewModel.resolveProblem(solution.problem_id)
                viewModel.addReflection(binding.reflectionContent.text.toString(), solution.problem_id)
                viewModel.updateSolutionSolvability(false, solution.solution_id)
                val action = ReflectionFragmentDirections.actionReflectionFragmentToProblemsFragment()
                findNavController().navigate(action)
            }
            .setPositiveButton(getString(R.string.go_back)) { _, _ ->
                val action = ReflectionFragmentDirections.actionReflectionFragmentToSolutionsFragment(solution.problem_id)
                findNavController().navigate(action)
            }
            .show()
    }

    private fun confirmFinalSolution(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.final_solution_confirmation))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteOtherSolutions()
                viewModel.resolveProblem(solution.problem_id)
                viewModel.addReflection(binding.reflectionContent.text.toString(), solution.problem_id)
                viewModel.updateSolutionSolvability(true, solution.solution_id)
                val action = ReflectionFragmentDirections.actionReflectionFragmentToProblemsFragment()
                findNavController().navigate(action)
            }
            .show()
    }

    private fun deleteOtherSolutions(){
        viewModel.deleteOtherSolutions(solution.solution_id, solution.problem_id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.retrieveSolution(navigationArgs.solutionId).observe(this.viewLifecycleOwner){ it ->
            solution = it
        }

        binding.buttonSave.setOnClickListener {
            if(binding.reflectionContent.text.toString().isNotBlank()){
                save()
            }
        }

    }
}
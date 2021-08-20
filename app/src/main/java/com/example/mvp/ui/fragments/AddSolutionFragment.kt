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
import com.example.mvp.data.entities.Solution
import com.example.mvp.databinding.FragmentAddSolutionBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

class AddSolutionFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: AddSolutionFragmentArgs by navArgs()

    lateinit var solution: Solution

    private var _binding: FragmentAddSolutionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddSolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.solutionTitle.text.toString(),
            binding.solutionDescription.text.toString()
        )
    }

    private fun addNewSolution(){
        if(isEntryValid()){
            val btnId = binding.yesOrNo.checkedRadioButtonId

            val title = binding.solutionTitle.text.toString()
            val description = binding.solutionDescription.text.toString()
            val solvable = binding.yesOrNo.findViewById<RadioButton>(btnId).text.toString() == "Yes"

            viewModel.addNewSolution(
                problem_id = navigationArgs.problemId,
                title = title,
                description = description,
                solvable = solvable
            )

            val action = AddSolutionFragmentDirections.actionAddSolutionFragmentToSolutionsFragment(navigationArgs.problemId)
            findNavController().navigate(action)
        }
    }

    private fun updateSolution(){
        val btnId = binding.yesOrNo.checkedRadioButtonId
        val title = binding.solutionTitle.text.toString()
        val description = binding.solutionDescription.text.toString()
        val solvable = binding.yesOrNo.findViewById<RadioButton>(btnId).text.toString() == "Yes"

        viewModel.updateSolution(
            solution_id = navigationArgs.solutionId,
            problem_id = navigationArgs.problemId,
            title = title,
            description = description,
            solvable = solvable
        )
    }

    private fun bindOldSolution(solution: Solution){
        binding.apply{
            solutionTitle.setText(solution.title, TextView.BufferType.SPANNABLE)
            solutionDescription.setText(solution.description, TextView.BufferType.SPANNABLE)
            if(solution.solvable){
                yesOrNo.check(btnYes.id)
            }else{
                yesOrNo.check(btnNo.id)
            }
        }

        binding.buttonSave.setOnClickListener {
            updateSolution()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.solutionId

        if(id>0){
            viewModel.retrieveSolution(id).observe(this.viewLifecycleOwner){ selectedSolution ->
                solution = selectedSolution
                bindOldSolution(solution)
            }
        }else{
            binding.buttonSave.setOnClickListener {
                addNewSolution()
            }
        }
    }
}
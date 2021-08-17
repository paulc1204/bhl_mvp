package com.example.mvp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvp.MyApplication
import com.example.mvp.data.entities.Problem
import com.example.mvp.databinding.FragmentAddProblemBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

class AddProblemFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: AddProblemFragmentArgs by navArgs()

    lateinit var problem: Problem

    private var _binding: FragmentAddProblemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProblemBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.problemTitle.text.toString(),
            binding.problemDescription.text.toString()
        )
    }

    private fun bindOldProblem(problem: Problem){
        binding.apply {
            problemTitle.setText(problem.title, TextView.BufferType.SPANNABLE)
            problemDescription.setText(problem.description, TextView.BufferType.SPANNABLE)

            toNext.setOnClickListener {
                if(isEntryValid()){
                    val action = AddProblemFragmentDirections.actionAddProblemFragmentToAddProblemCatFragment(
                        problemId = problem.problem_id,
                        category = problem.category,
                        title = problemTitle.text.toString(),
                        description = problemDescription.text.toString()
                    )
                    findNavController().navigate(action)
                }
            }

        }
    }

    private fun addNewProblem(){
        if(isEntryValid()){
            val title = binding.problemTitle.text.toString()
            val description = binding.problemDescription.text.toString()

            problem = viewModel.getPartialProblem(
                title = title,
                description = description
            )

            val action = AddProblemFragmentDirections.actionAddProblemFragmentToAddProblemCatFragment(
                problemId = problem.problem_id,
                title = title,
                description = description
            )
            findNavController().navigate(action)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.problemId

        if (id > 0){
            viewModel.retrieveProblem(id).observe(this.viewLifecycleOwner){ selectedProblem ->
                problem = selectedProblem
                bindOldProblem(problem)
            }
        }else{
            binding.toNext.setOnClickListener{
                addNewProblem()
            }
        }
    }

}
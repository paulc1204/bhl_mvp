package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvp.MyApplication
import com.example.mvp.data.entities.Solution
import com.example.mvp.databinding.FragmentEvalSolutionBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

class EvalSolutionFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
                (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: EvalSolutionFragmentArgs by navArgs()

    lateinit var solution: Solution

    private var _binding: FragmentEvalSolutionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEvalSolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean =
            (binding.pros.text.toString().isNotBlank() || binding.cons.text.toString().isNotBlank())

    private fun bind(){
        binding.apply {
            pros.setText(solution.pros?: "", TextView.BufferType.SPANNABLE)
            cons.setText(solution.cons?: "", TextView.BufferType.SPANNABLE)

            buttonSave.setOnClickListener {
                save()
            }
        }
    }

    private fun save(){
        if(isEntryValid()){
            viewModel.updateSolutionEval(
                    solution.solution_id,
                    binding.pros.text.toString(),
                    binding.cons.text.toString()
            )

            val action = EvalSolutionFragmentDirections.actionEvalSolutionFragmentToSecondSolutionsFragment(solution.problem_id)
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.solutionId

        viewModel.retrieveSolution(id).observe(this.viewLifecycleOwner){ selectedSolution ->
            solution = selectedSolution
            bind()
        }
    }


}
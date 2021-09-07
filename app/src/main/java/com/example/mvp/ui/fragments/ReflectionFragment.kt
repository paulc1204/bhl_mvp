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
import com.example.mvp.databinding.FragmentReflectionBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

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
        viewModel.resolveProblem(solution.problem_id)

        viewModel.addReflection(binding.reflectionContent.text.toString(), solution.problem_id)
        val action = ReflectionFragmentDirections.actionReflectionFragmentToProblemsFragment()
        findNavController().navigate(action)
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
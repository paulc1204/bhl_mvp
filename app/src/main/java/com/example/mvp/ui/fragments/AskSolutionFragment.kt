package com.example.mvp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvp.MyApplication
import com.example.mvp.databinding.FragmentAskSolutionBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

class AskSolutionFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: AskSolutionFragmentArgs by navArgs()

    private var _binding: FragmentAskSolutionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAskSolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onSolvable(problem_id: Int){
        viewModel.updateSolvability(true, problem_id)
        val action = AskSolutionFragmentDirections.actionAskSolutionFragmentToSolutionsFragment(problem_id)
        findNavController().navigate(action)
    }

    private fun onNotSolvable(){
        //navigate to distractions
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.problemId
        binding.btnYes.setOnClickListener { onSolvable(id) }
        binding.btnNo.setOnClickListener { onNotSolvable() }

    }

}
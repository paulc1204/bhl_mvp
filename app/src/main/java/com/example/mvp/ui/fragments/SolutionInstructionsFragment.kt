package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvp.databinding.FragmentSolutionInstructionsBinding

class SolutionInstructionsFragment: Fragment() {

    private var _binding: FragmentSolutionInstructionsBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: SolutionInstructionsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSolutionInstructionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOk.setOnClickListener {
            val id = navigationArgs.problemId
            if(id != -1){
                val action = SolutionInstructionsFragmentDirections.actionSolutionInstructionsFragmentToSolutionsFragment(navigationArgs.problemId)
                findNavController().navigate(action)
            }else{
                findNavController().navigateUp()
            }
        }

    }
}
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
import com.example.mvp.data.entities.Distraction
import com.example.mvp.data.entities.Solution
import com.example.mvp.databinding.FragmentAddDistractionBinding
import com.example.mvp.databinding.FragmentAddSolutionBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

class AddDistractionFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    private val navigationArgs: AddDistractionFragmentArgs by navArgs()

    lateinit var distraction: Distraction

    private var _binding: FragmentAddDistractionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddDistractionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun isEntryValid(): Boolean = binding.distractionTitle.text.toString().isNotEmpty()

    private fun addNewDistraction(){
        if(isEntryValid()){
            viewModel.addNewDistraction(binding.distractionTitle.text.toString())

            val action = AddDistractionFragmentDirections.actionAddDistractionFragmentToDistractionActivitiesFragment()
            findNavController().navigate(action)
        }


    }

    private fun updateDistraction(){
        if(isEntryValid()){
            viewModel.updateDistraction(distraction.distraction_id, binding.distractionTitle.text.toString())

            val action = AddDistractionFragmentDirections.actionAddDistractionFragmentToDistractionActivitiesFragment()
            findNavController().navigate(action)
        }
    }

    private fun bindOldDistraction(distraction: Distraction){
        binding.apply{
            distractionTitle.setText(distraction.title, TextView.BufferType.SPANNABLE)
        }

        binding.buttonSave.setOnClickListener {
            updateDistraction()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.distractionId

        if(id>0){
            viewModel.retrieveDistraction(id).observe(this.viewLifecycleOwner){ selectedDistraction ->
                distraction = selectedDistraction
                bindOldDistraction(distraction)
            }
        }else{
            binding.buttonSave.setOnClickListener {
                addNewDistraction()
            }
        }
    }
}
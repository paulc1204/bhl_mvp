package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mvp.MyApplication
import com.example.mvp.R
import com.example.mvp.databinding.FragmentHomeBinding
import com.example.mvp.databinding.FragmentIntroVideosBinding
import com.example.mvp.databinding.FragmentMoodTrackerBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

class MoodTrackerFragment: Fragment() {

    private var _binding: FragmentMoodTrackerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoodTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val moodLevels = resources.getStringArray(R.array.mood_level)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.mood_drop_down_item, moodLevels)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun save(){
        val level = binding.autoCompleteTextView.text.toString().toInt()
//        val level = binding.autoCompleteTextView.listSelection + 1
        viewModel.addNewMood(level)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            if(binding.autoCompleteTextView.text.isNotEmpty()){
                save()
            }
//            if(binding.autoCompleteTextView.listSelection != ListView.INVALID_POSITION){
//                save()
//            }
        }

    }
}
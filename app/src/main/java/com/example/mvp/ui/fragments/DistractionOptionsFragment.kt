package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mvp.databinding.FragmentDistractionOptionsBinding

class DistractionOptionsFragment: Fragment() {

    private var _binding: FragmentDistractionOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDistractionOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.howToDistract.setOnClickListener {
            val action = DistractionOptionsFragmentDirections.actionDistractionOptionsFragmentToDistractionVideosFragment()
            findNavController().navigate(action)
        }

        binding.toActivities.setOnClickListener {
            val action = DistractionOptionsFragmentDirections.actionDistractionOptionsFragmentToDistractionActivitiesFragment()
            findNavController().navigate(action)
        }
    }
}
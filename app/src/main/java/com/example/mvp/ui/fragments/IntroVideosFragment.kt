package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mvp.databinding.FragmentIntroVideosBinding

class IntroVideosFragment: Fragment() {

    private var _binding: FragmentIntroVideosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroVideosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toNext.setOnClickListener {
            val action = IntroVideosFragmentDirections.actionIntroVideosFragmentToProblemsFragment()
            findNavController().navigate(action)
        }
    }
}
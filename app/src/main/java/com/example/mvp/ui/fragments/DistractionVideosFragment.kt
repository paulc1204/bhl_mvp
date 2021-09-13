package com.example.mvp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mvp.databinding.FragmentDistractionVideosBinding

class DistractionVideosFragment: Fragment() {

    private var _binding: FragmentDistractionVideosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDistractionVideosBinding.inflate(inflater, container, false)
        return binding.root
    }
}
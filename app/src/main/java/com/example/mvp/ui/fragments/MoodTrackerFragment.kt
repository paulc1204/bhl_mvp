package com.example.mvp.ui.fragments

import android.graphics.Color
import android.graphics.Color.green
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
import com.example.mvp.data.entities.Mood
import com.example.mvp.databinding.FragmentHomeBinding
import com.example.mvp.databinding.FragmentIntroVideosBinding
import com.example.mvp.databinding.FragmentMoodTrackerBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.format.DateTimeFormatter

class MoodTrackerFragment: Fragment() {

    private var _binding: FragmentMoodTrackerBinding? = null
    private val binding get() = _binding!!

    lateinit var moodLineChart: LineChart

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

    //maintain spinner option items when fragment resumes
    override fun onResume() {
        super.onResume()
        val moodLevels = resources.getStringArray(R.array.mood_level)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.mood_drop_down_item, moodLevels)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun save(){
        val level = binding.autoCompleteTextView.text.toString().toInt()
        viewModel.addNewMood(level)
        showConfirmMessage()
    }

    private fun showConfirmMessage(){
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.anxiety_added))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
            }
            .show()
    }

    private fun setLineChart(moodLevels: List<Mood>){
        val xAxis = moodLineChart.xAxis
        val yAxis = moodLineChart.axisLeft

        val xValues = ArrayList<String>()
        for(mood in moodLevels){
            xValues.add(mood.timestamp.format(DateTimeFormatter.ofPattern("MM-dd")))
        }

        val lineEntries = ArrayList<Entry>()
        for((i, mood) in moodLevels.withIndex()){
            lineEntries.add(
                Entry(i.toFloat(), mood.level.toFloat() )
            )
        }

        val lineDataSet = LineDataSet(lineEntries, "Anxiety Levels")
        lineDataSet.color = Color.rgb(153, 51, 255)
        lineDataSet.valueTextSize = 9f
        val data = LineData(lineDataSet)

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f

        moodLineChart.axisRight.isEnabled = false   //disable y-axis label on the right
        moodLineChart.description.isEnabled = false
        moodLineChart.legend.isEnabled = false
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 10f

        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        moodLineChart.data = data

        moodLineChart.setBackgroundColor(Color.rgb(224, 224, 224))
        moodLineChart.setScaleEnabled(false)
        moodLineChart.setPinchZoom(false)
        moodLineChart.setDrawGridBackground(false)
        moodLineChart.setExtraOffsets(15f, 0f, 15f, 0f)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moodLineChart = binding.moodLineChart

        viewModel.moodLevels.observe(this.viewLifecycleOwner){ moodLevels ->
            setLineChart(moodLevels.reversed())
        }

        binding.btnSave.setOnClickListener {
            if(binding.autoCompleteTextView.text.isNotEmpty()){
                save()
            }
        }

    }
}


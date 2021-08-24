package com.example.mvp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvp.MyApplication
import com.example.mvp.data.entities.Problem
import com.example.mvp.databinding.FragmentAddProblemCatBinding
import com.example.mvp.viewmodels.ProblemLogViewModel
import com.example.mvp.viewmodels.ProblemLogViewModelFactory

class AddProblemCatFragment: Fragment() {

    private val viewModel: ProblemLogViewModel by activityViewModels {
        ProblemLogViewModelFactory(
            (activity?.application as MyApplication).database.problemDao()
        )
    }
    lateinit var problem: Problem
    private val navigationArgs: AddProblemCatFragmentArgs by navArgs()

    private var _binding: FragmentAddProblemCatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProblemCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun addNewProblem(){

        val btnId = binding.categoryOptions.checkedRadioButtonId
        val category = binding.categoryOptions.findViewById<RadioButton>(btnId).text.toString()

        viewModel.addNewProblem(
            navigationArgs.problemId,
            navigationArgs.title,
            navigationArgs.description,
            category
        )

       val action = AddProblemCatFragmentDirections.actionAddProblemCatFragmentToAskSolutionFragment(viewModel.getLatestProblemId())
       findNavController().navigate(action)
    }

    private fun updateProblem(category: String){

        viewModel.updateCategory(
            title = navigationArgs.title,
            description = navigationArgs.description,
            category = category,
            problem_id = navigationArgs.problemId
        )

        if(problem.solvable!!){
           val action = AddProblemCatFragmentDirections.actionAddProblemCatFragmentToSolutionsFragment(navigationArgs.problemId)
            findNavController().navigate(action)
        }else{
           val action = AddProblemCatFragmentDirections.actionAddProblemCatFragmentToAskSolutionFragment(navigationArgs.problemId)
           findNavController().navigate(action)
        }

    }

    private fun bindOldProblem(problem: Problem){
        var btn: RadioButton

        binding.apply{
            for(i in 0 until categoryOptions.childCount){
                btn = categoryOptions.getChildAt(i) as RadioButton

                if(btn.text.toString() == problem.category){
                    categoryOptions.check(btn.id)
                    break
                }
            }

            buttonSave.setOnClickListener {
                val btnId = binding.categoryOptions.checkedRadioButtonId
                val category = binding.categoryOptions.findViewById<RadioButton>(btnId).text.toString()
                updateProblem(category)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.problemId
        val category: String? = navigationArgs.category

        Log.d("CatFrag", "problem_id: $id")

        if(!category.isNullOrEmpty()){
            viewModel.retrieveProblem(id).observe(this.viewLifecycleOwner){ selectedProblem ->
                problem = selectedProblem
                bindOldProblem(problem)
            }
        }else{
            binding.buttonSave.setOnClickListener {
                addNewProblem()
            }
        }
    }
}
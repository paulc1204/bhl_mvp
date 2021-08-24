package com.example.mvp.viewmodels

import android.util.Log
import android.util.Log.*
import androidx.lifecycle.*
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.ProblemDao
import com.example.mvp.data.entities.Solution
import com.example.mvp.data.relations.ProblemWIthSolutions
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

class ProblemLogViewModel(private val problemDao: ProblemDao): ViewModel() {

    val problems: LiveData<List<Problem>> = problemDao.getProblems().asLiveData()
    val problemsWIthSolutions: LiveData<List<ProblemWIthSolutions>> = problemDao.getProblemsWithSolutions().asLiveData()

    fun updateCategory(title: String, description: String, category: String, problem_id: Int){
        viewModelScope.launch {
            problemDao.update(title, description, category, problem_id)
        }
    }

    fun updateSolvability(solvable: Boolean, problem_id: Int){
        viewModelScope.launch {
            problemDao.update(solvable, problem_id)
        }
    }

    /*
    * Return id of last added problem entry
    * passed to [SolutionsFragment] to query corresponding solutions
    * */
    fun getLatestProblemId(): Int = runBlocking{
            problemDao.getLatestProblemId()
        }


    private fun updateProblem(problem: Problem){
        viewModelScope.launch {
            problemDao.update(problem)
        }
    }

    fun updateSolution(
        solution_id: Int,
        problem_id: Int,
        title: String,
        description: String,
        solvable: Boolean
    ){
        val updatedSolution = getUpdatedSolutionEntry(solution_id, problem_id, title, description, solvable)
        updateSolution(updatedSolution)
    }

    private fun updateSolution(solution: Solution){
        viewModelScope.launch {
            problemDao.updateSolution(solution)
        }
    }

    /*
    * Get Problem instance in [AddProblemFragment]
    * */
    fun getPartialProblem(title: String, description: String): Problem {
        return Problem(title = title, description = description)
    }

    fun addNewProblem(problem_id: Int, title: String, description: String, category: String?){
        val newProblem = getNewProblemEntry(problem_id, title, description, category)
        insertProblem(newProblem)
    }

    fun addNewSolution(problem_id: Int, title: String, description: String, solvable: Boolean){
        val newSolution = getNewSolutionEntry(problem_id, title, description, solvable)
        insertSolution(newSolution)
    }

    private fun getNewProblemEntry(problem_id: Int, title: String, description: String, category: String?): Problem {
        return Problem(
            problem_id = problem_id,
            title = title,
            description = description,
            category = category,
            timestamp = LocalDateTime.now()
        )
    }

    private fun getNewSolutionEntry(problem_id: Int, title: String, description: String, solvable: Boolean): Solution{
        return Solution(
            problem_id = problem_id,
            title = title,
            description = description,
            solvable = solvable
        )
    }

    private fun getUpdatedSolutionEntry(
        solution_id: Int,
        problem_id: Int,
        title: String,
        description: String,
        solvable: Boolean
    ): Solution{
        return Solution(solution_id, problem_id, title, description, solvable)
    }

    /*
    * Checks that the fields are not empty
    * */
    fun isEntryValid(title: String, description: String): Boolean{
        return !(title.isBlank() || description.isBlank())
    }

    fun retrieveProblem(problem_id: Int): LiveData<Problem>{
        return problemDao.getProblem(problem_id).asLiveData()
    }

    fun retrieveSolution(solution_id: Int): LiveData<Solution>{
        return problemDao.getSolution(solution_id).asLiveData()
    }

    private fun insertProblem(problem: Problem){
        viewModelScope.launch {
            problemDao.insert(problem)
        }
    }

    private fun insertSolution(solution: Solution){
        viewModelScope.launch {
            problemDao.insertSolution(solution)
        }
    }

    fun deleteProblem(problem: Problem){
        viewModelScope.launch {
            problemDao.delete(problem)
        }
    }

    fun deleteSolution(solution: Solution){
        viewModelScope.launch {
            problemDao.delete(solution)
        }
    }

    fun deleteSolutions(problem_id: Int){
        viewModelScope.launch {
            problemDao.deleteSolutions(problem_id)
        }
    }
}


class ProblemLogViewModelFactory(private val problemDao: ProblemDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProblemLogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProblemLogViewModel(problemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
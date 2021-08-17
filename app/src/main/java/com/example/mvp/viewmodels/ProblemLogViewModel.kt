package com.example.mvp.viewmodels

import androidx.lifecycle.*
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.ProblemDao
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ProblemLogViewModel(private val problemDao: ProblemDao): ViewModel() {

    val problems: LiveData<List<Problem>> = problemDao.getProblems().asLiveData()

    fun updateProblem(
        problem_id: Int,
        title: String,
        description: String,
        category: String
    ){
        val updatedProblem = getUpdatedProblemEntry(problem_id, title, description, category)
        updateProblem(updatedProblem)
    }

    private fun updateProblem(problem: Problem){
        viewModelScope.launch {
            problemDao.update(problem)
        }
    }

    private fun getUpdatedProblemEntry(
        problem_id: Int,
        title: String,
        description: String,
        category: String
    ): Problem {
        return Problem(
            problem_id = problem_id,
            title = title,
            description = description,
            category = category,
            timestamp = LocalDateTime.now()
        )
    }

    /*
    * Get Problem instance in AddProblemFragment
    * */
    fun getPartialProblem(title: String, description: String): Problem {
        return Problem(title = title, description = description)
    }

    fun addNewProblem(problem_id: Int, title: String, description: String, category: String?){
        val newProblem = getNewProblemEntry(problem_id, title, description, category)
        insertProblem(newProblem)
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

    /*
    * Checks that the fields are not empty
    * */
    fun isEntryValid(title: String, description: String): Boolean{
        return !(title.isBlank() || description.isBlank())
    }

    fun retrieveProblem(problem_id: Int): LiveData<Problem>{
        return problemDao.getProblem(problem_id).asLiveData()
    }


    private fun insertProblem(problem: Problem){
        viewModelScope.launch {
            problemDao.insert(problem)
        }
    }

    fun deleteProblem(problem: Problem){
        viewModelScope.launch {
            problemDao.delete(problem)
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
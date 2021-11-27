package com.example.mvp.viewmodels

import android.util.Log
import android.util.Log.*
import androidx.lifecycle.*
import com.example.mvp.data.ProblemDao
import com.example.mvp.data.entities.*
import com.example.mvp.data.relations.ProblemWIthSolutions
import com.example.mvp.data.relations.SolutionWithCons
import com.example.mvp.data.relations.SolutionWithPros
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProblemLogViewModel(private val problemDao: ProblemDao): ViewModel() {

    val problems: LiveData<List<Problem>> = problemDao.getProblems().asLiveData()
    val problemsWIthSolutions: LiveData<List<ProblemWIthSolutions>> = problemDao.getProblemsWithSolutions().asLiveData()
    val solutionsWithPros: LiveData<List<SolutionWithPros>> = problemDao.getSolutionsWithPros().asLiveData()
    val solutionsWithCons: LiveData<List<SolutionWithCons>> = problemDao.getSolutionsWithCons().asLiveData()
    val distractions: LiveData<List<Distraction>> = problemDao.getDistractions().asLiveData()
    val moodLevels: LiveData<List<Mood>> = problemDao.getMoods().asLiveData()

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

    fun updateSolutionSolvability(solvable: Boolean, solution_id: Int){
        viewModelScope.launch {
            problemDao.updateSolutionSolvability(solvable, solution_id)
        }
    }

//    fun updateSolutionEval(solution_id: Int, pros: String, cons: String){
//        viewModelScope.launch {
//            problemDao.updateSolutionEval(pros, cons, solution_id)
//        }
//    }

    /*
    * Return id of last added problem entry
    * passed to [SolutionsFragment] to query corresponding solutions
    * */
    fun getLatestProblemId(): Int = runBlocking{
        problemDao.getLatestProblemId()
    }

    fun updateSolution(
        solution_id: Int,
        title: String,
        description: String
    ){
        viewModelScope.launch {
            problemDao.updateSolution(solution_id, title, description)
        }
    }

    fun updateDistraction(distraction_id: Int, title: String){
        viewModelScope.launch {
            problemDao.updateDistraction(title, distraction_id)
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

    fun resolveProblem(problem_id: Int){
        viewModelScope.launch {
            problemDao.resolveProblem(problem_id)
        }
    }

    fun addReflection(reflection: String, problem_id: Int){
        viewModelScope.launch {
            problemDao.addReflection(reflection, problem_id)
        }
    }

    fun addNewSolution(problem_id: Int, title: String, description: String){
        val newSolution = getNewSolutionEntry(problem_id, title, description)
        insertSolution(newSolution)
    }

    fun addNewPro(solution_id: Int, description: String){
        val pro = getNewProEntry(solution_id, description)
        insertPro(pro)
    }

    fun addNewCon(solution_id: Int, description: String){
        val con = getNewConEntry(solution_id, description)
        insertCon(con)
    }

    fun addNewMood(level: Int){
        val mood = Mood(LocalDate.now(), level)
        insertMood(mood)
    }

    fun addNewDistraction(title: String){
        val newDistraction = getNewDistractionEntry(title)
        insertDistraction(newDistraction)
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

    private fun getNewSolutionEntry(problem_id: Int, title: String, description: String): Solution{
        return Solution(
            problem_id = problem_id,
            title = title,
            description = description
        )
    }

    private fun getNewProEntry(solution_id: Int, description: String): Pros{
        return Pros(solution_id = solution_id, description = description)
    }

    private fun getNewConEntry(solution_id: Int, description: String): Cons{
        return Cons(solution_id = solution_id, description = description)
    }

    private fun getNewDistractionEntry(title: String): Distraction = Distraction(title = title)

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

    fun retrieveDistraction(distraction_id: Int): LiveData<Distraction>{
        return problemDao.getDistraction(distraction_id).asLiveData()
    }

    private fun insertProblem(problem: Problem){
        viewModelScope.launch {
            problemDao.insertProblem(problem)
        }
    }

    private fun insertSolution(solution: Solution){
        viewModelScope.launch {
            problemDao.insertSolution(solution)
        }
    }

    private fun insertPro(pro: Pros){
        viewModelScope.launch {
            problemDao.insertPro(pro)
        }
    }

    private fun insertCon(con: Cons){
        viewModelScope.launch {
            problemDao.insertCon(con)
        }
    }

    private fun insertDistraction(distraction: Distraction){
        viewModelScope.launch {
            problemDao.insertDistraction(distraction)
        }
    }

    private fun insertMood(mood: Mood){
        viewModelScope.launch {
            problemDao.insertMood(mood)
        }
    }

    private fun insertReflection(reflection: Reflection){
        viewModelScope.launch {
            problemDao.insertReflection(reflection)
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

    fun deletePro(pro: Pros){
        viewModelScope.launch {
            problemDao.delete(pro)
        }
    }

    fun deleteCon(con: Cons){
        viewModelScope.launch {
            problemDao.delete(con)
        }
    }

    fun deleteSolutions(problem_id: Int){
        viewModelScope.launch {
            problemDao.deleteSolutions(problem_id)
        }
    }

    fun deletePros(solution_id: Int){
        viewModelScope.launch {
            problemDao.deletePros(solution_id)
        }
    }

    fun deleteCons(solution_id: Int){
        viewModelScope.launch {
            problemDao.deleteCons(solution_id)
        }
    }

    fun deleteOtherSolutions(solution_id: Int, problem_id: Int){
        viewModelScope.launch {
            problemDao.deleteOtherSolutions(solution_id, problem_id)
        }
    }

    fun deleteDistraction(distraction: Distraction){
        viewModelScope.launch {
            problemDao.delete(distraction)
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
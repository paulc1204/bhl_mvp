package com.example.mvp.data

import androidx.room.*
import com.example.mvp.data.entities.*
import com.example.mvp.data.relations.ProblemWIthSolutions
import com.example.mvp.data.relations.SolutionWithCons
import com.example.mvp.data.relations.SolutionWithPros
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ProblemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProblem(problem: Problem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSolution(solution: Solution)

    @Insert
    suspend fun insertDistraction(distraction: Distraction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: Mood)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReflection(reflection: Reflection)

    @Insert
    suspend fun insertPro(pro: Pros)

    @Insert
    suspend fun insertCon(con: Cons)

    @Transaction
    @Query("SELECT * from problems")
    fun getProblemsWithSolutions(): Flow<List<ProblemWIthSolutions>>

    @Transaction
    @Query("SELECT * from solutions")
    fun getSolutionsWithPros(): Flow<List<SolutionWithPros>>

    @Transaction
    @Query("SELECT * from solutions")
    fun getSolutionsWithCons(): Flow<List<SolutionWithCons>>

    @Query("SELECT * from problems")
    fun getProblems(): Flow<List<Problem>>

    @Query("SELECT * from problems WHERE problem_id = :problem_id")
    fun getProblem(problem_id: Int): Flow<Problem>

    @Query("SELECT problem_id from problems ORDER BY problem_id DESC LIMIT 1")
    suspend fun getLatestProblemId(): Int

    @Query("UPDATE problems SET solved = 1 WHERE problem_id = :problem_id")
    suspend fun resolveProblem(problem_id: Int)

    @Query("UPDATE problems SET reflection = :reflection WHERE problem_id = :problem_id")
    suspend fun addReflection(reflection: String, problem_id: Int)

    @Query("SELECT * from solutions WHERE solution_id = :solution_id")
    fun getSolution(solution_id: Int): Flow<Solution>

    @Query("SELECT * from distractions WHERE distraction_id = :distraction_id")
    fun getDistraction(distraction_id: Int): Flow<Distraction>

    @Query("SELECT * from distractions")
    fun getDistractions(): Flow<List<Distraction>>

    @Query("SELECT * from mood ORDER BY timestamp DESC LIMIT 7 ")
    fun getMoods(): Flow<List<Mood>>

    @Query("SELECT timestamp from problems WHERE problem_id = :problem_id")
    fun getTimestamp(problem_id: Int): LocalDateTime

    @Query("UPDATE problems SET title = :title, description = :description, category = :category WHERE problem_id = :problem_id")
    suspend fun update(title: String, description: String, category: String, problem_id: Int)

    @Query("UPDATE problems SET solvable = :solvable WHERE problem_id = :problem_id")
    suspend fun update(solvable: Boolean, problem_id: Int)

    @Query("UPDATE distractions SET title = :title WHERE distraction_id = :distraction_id")
    suspend fun updateDistraction(title: String, distraction_id: Int)

    @Update
    suspend fun update(problem: Problem)

    @Update
    suspend fun updateSolution(solution: Solution)

    @Query("UPDATE solutions SET title = :title, description = :description WHERE solution_id = :solution_id")
    suspend fun updateSolution(solution_id: Int, title: String, description: String)

//    @Query("UPDATE solutions SET pros = :pros, cons = :cons WHERE solution_id = :solution_id")
//    suspend fun updateSolutionEval(pros: String, cons: String, solution_id: Int)

    @Query("UPDATE solutions SET solvable = :solvable WHERE solution_id = :solution_id")
    suspend fun updateSolutionSolvability(solvable: Boolean, solution_id: Int)

    @Query("DELETE from solutions WHERE problem_id = :problem_id")
    suspend fun deleteSolutions(problem_id: Int)

    @Query("DELETE from pros WHERE solution_id = :solution_id")
    suspend fun deletePros(solution_id: Int)

    @Query("DELETE from cons WHERE solution_id = :solution_id")
    suspend fun deleteCons(solution_id: Int)

    @Query("DELETE from solutions WHERE solution_id != :solution_id AND problem_id = :problem_id")
    suspend fun deleteOtherSolutions(solution_id: Int, problem_id: Int)

    @Delete
    suspend fun delete(problem: Problem)

    @Delete
    suspend fun delete(solution: Solution)

    @Delete
    suspend fun delete(pro: Pros)

    @Delete
    suspend fun delete(con: Cons)

    @Delete
    suspend fun delete(distraction: Distraction)
}
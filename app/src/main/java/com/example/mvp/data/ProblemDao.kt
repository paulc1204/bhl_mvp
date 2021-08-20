package com.example.mvp.data

import androidx.room.*
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.entities.Solution
import com.example.mvp.data.relations.ProblemWIthSolutions
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ProblemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(problem: Problem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSolution(solution: Solution)

    @Transaction
    @Query("SELECT * from problems WHERE problem_id = :problem_id")
    fun getProblemWithSolutions(problem_id: Int): Flow<ProblemWIthSolutions>

    @Transaction
    @Query("SELECT * from problems")
    fun getProblemsWithSolutions(): Flow<List<ProblemWIthSolutions>>

    @Query("SELECT * from problems")
    fun getProblems(): Flow<List<Problem>>

    @Query("SELECT * from problems WHERE problem_id = :problem_id")
    fun getProblem(problem_id: Int): Flow<Problem>

    @Query("SELECT * from solutions WHERE solution_id = :solution_id")
    fun getSolution(solution_id: Int): Flow<Solution>

    @Query("SELECT timestamp from problems WHERE problem_id = :problem_id")
    fun getTimestamp(problem_id: Int): LocalDateTime

    @Query("UPDATE problems SET title = :title, description = :description, category = :category WHERE problem_id = :problem_id")
    suspend fun update(title: String, description: String, category: String, problem_id: Int)

    @Query("UPDATE problems SET solvable = :solvable WHERE problem_id = :problem_id")
    suspend fun update(solvable: Boolean, problem_id: Int)

    @Update
    suspend fun update(problem: Problem)

    @Update
    suspend fun updateSolution(solution: Solution)

    @Delete
    suspend fun delete(problem: Problem)

    @Delete
    suspend fun delete(solution: Solution)
}
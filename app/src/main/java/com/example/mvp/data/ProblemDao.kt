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
    fun getProblemWithSolutions(problem_id: Int): Flow<List<ProblemWIthSolutions>>

    @Query("SELECT * from problems")
    fun getProblems(): Flow<List<Problem>>

    @Query("SELECT * from problems WHERE problem_id = :problem_id")
    fun getProblem(problem_id: Int): Flow<Problem>

    @Update
    suspend fun update(problem: Problem)

    @Delete
    suspend fun delete(problem: Problem)
}
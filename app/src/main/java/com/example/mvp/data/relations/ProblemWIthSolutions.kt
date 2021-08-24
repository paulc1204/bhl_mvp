package com.example.mvp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.entities.Solution

data class ProblemWIthSolutions (
    @Embedded val problem: Problem,
    @Relation(
        parentColumn = "problem_id",
        entityColumn = "problem_id"
    )
    val solutions: List<Solution>?
)
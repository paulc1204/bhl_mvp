package com.example.mvp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mvp.data.entities.Cons
import com.example.mvp.data.entities.Solution

data class SolutionWithCons (
    @Embedded val solution: Solution,
    @Relation(
        parentColumn = "solution_id",
        entityColumn = "solution_id"
    )
    val cons: List<Cons>?
)
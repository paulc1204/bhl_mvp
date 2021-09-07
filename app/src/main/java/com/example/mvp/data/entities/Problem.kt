package com.example.mvp.data.entities

import androidx.room.*
import java.time.LocalDateTime

@Entity(tableName = "problems")
data class Problem(

    @PrimaryKey(autoGenerate = true)
    val problem_id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "category")
    val category: String? = null,
    @ColumnInfo(name = "timestamp")
    val timestamp: LocalDateTime? = null,
    @ColumnInfo(name = "solvable")
    val solvable: Boolean? = false,
    @ColumnInfo(name = "solved")
    val solved: Boolean? = false,
    @ColumnInfo(name = "reflection")
    val reflection: String? = null,
    @ColumnInfo(name = "distraction_id")
    val distraction_id: Int? = null
)

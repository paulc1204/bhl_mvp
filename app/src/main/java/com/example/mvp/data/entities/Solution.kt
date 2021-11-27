package com.example.mvp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "solutions")
data class Solution(

    @PrimaryKey(autoGenerate = true)
    val solution_id: Int = 0,
    @ColumnInfo(name = "problem_id")
    val problem_id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "solvable")
    val solvable: Boolean? = null,
    @ColumnInfo(name = "tried")
    val tried: Boolean? = false,
    @ColumnInfo(name = "helful")
    val helpful: Boolean? = null,
    @ColumnInfo(name = "sched_time")
    val sched_time: LocalDateTime? = null
)

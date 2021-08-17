package com.example.mvp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    val solvable: Boolean
)

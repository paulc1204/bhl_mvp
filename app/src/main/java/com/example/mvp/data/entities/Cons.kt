package com.example.mvp.data.entities

import androidx.room.*

@Entity(tableName = "cons")
data class Cons(
    @PrimaryKey(autoGenerate = true)
    val con_id: Int = 0,
    @ColumnInfo(name = "solution_id")
    val solution_id: Int,
    @ColumnInfo(name = "description")
    val description: String
)

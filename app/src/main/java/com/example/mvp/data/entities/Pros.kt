package com.example.mvp.data.entities

import androidx.room.*

@Entity(tableName = "pros")
data class Pros(
    @PrimaryKey(autoGenerate = true)
    val pro_id: Int = 0,
    @ColumnInfo(name = "solution_id")
    val solution_id: Int,
    @ColumnInfo(name = "description")
    val description: String
)

package com.example.mvp.data.entities

import androidx.room.*
import java.time.LocalDate

@Entity(tableName = "mood")
data class Mood(

    @PrimaryKey
    val timestamp: LocalDate,
    @ColumnInfo(name = "level")
    val level: Int
)

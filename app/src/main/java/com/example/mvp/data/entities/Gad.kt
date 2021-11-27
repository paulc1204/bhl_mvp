package com.example.mvp.data.entities

import androidx.room.*
import java.time.LocalDateTime

@Entity(tableName = "gad")
data class Gad(
    @PrimaryKey(autoGenerate = true)
    val gad_id: Int = 0,
    @ColumnInfo(name = "test_date")
    override val timestamp: LocalDateTime,
    @ColumnInfo(name = "score")
    override val score: Int
): Measurement

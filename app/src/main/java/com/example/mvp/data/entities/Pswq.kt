package com.example.mvp.data.entities

import androidx.room.*
import java.time.LocalDateTime

@Entity(tableName = "pswq")
data class Pswq(
    @PrimaryKey(autoGenerate = true)
    val pswq_id: Int = 0,
    @ColumnInfo(name = "test_date")
    override val timestamp: LocalDateTime,
    @ColumnInfo(name = "score")
    override val score: Int
): Measurement

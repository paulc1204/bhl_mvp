package com.example.mvp.data.entities

import androidx.room.*
import java.time.LocalDateTime

@Entity(tableName = "overthinking")
data class Overthinking(
    @PrimaryKey(autoGenerate = true)
    val overthinking_id: Int = 0,
    @ColumnInfo(name = "title")
    override val title: String,
    @ColumnInfo(name = "description")
    override val description: String,
    @ColumnInfo(name = "timestamp")
    override val timestamp: LocalDateTime,
    ): Log

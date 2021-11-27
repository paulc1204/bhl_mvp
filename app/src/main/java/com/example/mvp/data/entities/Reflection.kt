package com.example.mvp.data.entities

import androidx.room.*
import java.time.LocalDateTime

@Entity(tableName = "reflections")
data class Reflection(
    @PrimaryKey(autoGenerate = true)
    val reflection_id: Int = 0,
    @ColumnInfo(name = "title")
    override val title: String,
    @ColumnInfo(name = "description")
    override val description: String,
    @ColumnInfo(name = "timestamp")
    override val timestamp: LocalDateTime,
    ): Log

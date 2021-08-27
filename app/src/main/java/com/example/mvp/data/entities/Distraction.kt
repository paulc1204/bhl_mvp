package com.example.mvp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "distractions")
data class Distraction(

    @PrimaryKey(autoGenerate = true)
    val distraction_id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String
)

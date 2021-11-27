package com.example.mvp.data.entities

import java.time.LocalDateTime

interface Measurement {
    val timestamp: LocalDateTime
    val score: Int
}
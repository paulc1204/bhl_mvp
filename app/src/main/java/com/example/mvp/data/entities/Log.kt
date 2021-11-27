package com.example.mvp.data.entities

import java.time.LocalDateTime

interface Log {
    val title: String
    val description: String
    val timestamp: LocalDateTime
}
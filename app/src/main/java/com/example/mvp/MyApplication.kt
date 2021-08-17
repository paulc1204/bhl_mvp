package com.example.mvp

import android.app.Application
import com.example.mvp.data.ProblemRoomDatabase

class MyApplication: Application() {
    val database: ProblemRoomDatabase by lazy { ProblemRoomDatabase.getDatabase(this) }
}
package com.example.mvp.data

import android.content.Context
import androidx.room.*
import com.example.mvp.data.entities.Distraction
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.entities.Solution

@Database(
    entities = [
        Problem::class,
        Solution::class,
        Distraction::class
    ],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3)
    ]
)
@TypeConverters(Converters::class)
abstract class ProblemRoomDatabase: RoomDatabase() {

    abstract fun problemDao(): ProblemDao

    companion object{
        @Volatile
        private var INSTANCE: ProblemRoomDatabase? = null

        fun getDatabase(context: Context): ProblemRoomDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProblemRoomDatabase::class.java,
                    "problem_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
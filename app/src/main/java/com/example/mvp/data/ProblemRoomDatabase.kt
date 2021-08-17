package com.example.mvp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.entities.Solution

@Database(
    entities = [
        Problem::class,
        Solution::class
    ],
    version = 1
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
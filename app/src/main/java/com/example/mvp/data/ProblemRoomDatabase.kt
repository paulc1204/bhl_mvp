package com.example.mvp.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.example.mvp.data.entities.Distraction
import com.example.mvp.data.entities.Mood
import com.example.mvp.data.entities.Problem
import com.example.mvp.data.entities.Solution

@Database(
    entities = [
        Problem::class,
        Solution::class,
        Distraction::class,
        Mood::class
    ],
    version = 8,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
        AutoMigration(from = 5, to = 6, spec = ProblemRoomDatabase.ProblemsAutoMigration::class),
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8)
    ]
)
@TypeConverters(Converters::class)
abstract class ProblemRoomDatabase: RoomDatabase() {

    @DeleteTable(tableName = "mood")
    class ProblemsAutoMigration: AutoMigrationSpec {}

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
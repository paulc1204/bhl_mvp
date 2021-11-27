package com.example.mvp.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.example.mvp.data.entities.*

@Database(
    entities = [
        Problem::class,
        Solution::class,
        Reflection::class,
        Distraction::class,
        Mood::class,
        Overthinking::class,
        Pros::class,
        Cons::class,
        Gad::class,
        Pswq::class
    ],
    version = 9,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
        AutoMigration(from = 5, to = 6, spec = ProblemRoomDatabase.ProblemsAutoMigration::class),
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8),
        AutoMigration(from = 8, to = 9, spec = ProblemRoomDatabase.VersionNineAutoMigration::class)
    ]
)
@TypeConverters(Converters::class)
abstract class ProblemRoomDatabase: RoomDatabase() {

    @DeleteTable(tableName = "mood")
    class ProblemsAutoMigration: AutoMigrationSpec {}

    @DeleteColumn.Entries(
        DeleteColumn(tableName = "solutions", columnName = "pros"),
        DeleteColumn(tableName = "solutions", columnName = "cons")
    )
    class VersionNineAutoMigration: AutoMigrationSpec {}

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
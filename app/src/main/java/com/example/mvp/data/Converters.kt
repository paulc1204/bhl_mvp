package com.example.mvp.data

import android.os.Build
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun fromDateTime(timestamp: LocalDateTime?): String{
        return timestamp.toString()
    }

    @TypeConverter
    fun toDateTime(timeString: String?): LocalDateTime?{
        return LocalDateTime.parse(
            timeString,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        )
    }

    @TypeConverter
    fun fromDate(date: LocalDate?): String{
        return date.toString()
    }

    @TypeConverter
    fun toDate(dateString: String?): LocalDate?{
        return LocalDate.parse(
            dateString,
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )
    }
}
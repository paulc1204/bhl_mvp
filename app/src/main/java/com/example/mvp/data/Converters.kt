package com.example.mvp.data

import android.os.Build
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun fromDateTime(timestamp: LocalDateTime?): String?{
        return timestamp.toString()
    }

    @TypeConverter
    fun toDateTime(timeString: String?): LocalDateTime?{

        return LocalDateTime.parse(
            timeString,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        )
    }
}
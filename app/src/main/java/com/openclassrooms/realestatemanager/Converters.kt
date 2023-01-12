package com.openclassrooms.realestatemanager

import androidx.room.TypeConverter
import java.time.*
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimeStamp(value: String): LocalDate {
        return value.let { LocalDate.parse(value) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: LocalDate): String {
        return date.toString()
    }
}
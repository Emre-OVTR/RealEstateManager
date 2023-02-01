package com.openclassrooms.realestatemanager

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
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

    @TypeConverter
    fun latLngToString(latLng: LatLng) : String{
        return "(${latLng.latitude},${latLng.longitude}"
    }

    @TypeConverter
    fun stringToLatLng(string: String) : LatLng{
        val s = string.replace("(", "").replace(")", "")
        val list = s.split(",")
        return LatLng(list.first().toDouble(), list.last().toDouble())
    }
}
package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.openclassrooms.realestatemanager.Converters
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "estate_table")
@TypeConverters(Converters::class)
data class Estate(
    @SerializedName("estate")
   // var estates: List<Estate> = listOf()
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val price: Int,
    val description : String,
      var isNearParks : Boolean,
     val isNearShops : Boolean,
    val isNearSchools: Boolean,
    val isNearHighway: Boolean,
    val estateType: String,
   // val borough: String,
    val surface: Int,
    val roomNumber: Int,
    val bathroomNumber: Int,
    val bedRoomNumber: Int,
    val address: String,
    val longitude : Double,
    val latitude : Double,

    val creationDate : LocalDate
): Serializable


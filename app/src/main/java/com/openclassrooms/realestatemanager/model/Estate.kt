package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


data class Estate(
    @SerializedName("estate")
    var estates: List<Estate> = listOf()
): Serializable {


    @Entity(tableName = "estate_table")
    data class EstateEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val price: Int,
        val estateType: String,
        val borough: String,
        val surface: Int,
        val roomNumber: Int,
        val bathroomNumber: Int,
        val bedRoomNumber: Int,
        val address: String
    ) : Serializable


}
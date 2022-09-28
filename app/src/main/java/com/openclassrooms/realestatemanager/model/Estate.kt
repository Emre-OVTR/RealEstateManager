package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity (tableName = "estate_table")
data class Estate(@PrimaryKey(autoGenerate = true) var id : Long,
                  var estateType:String,
                  var price: Int,
                  var borough: String,
                  var surface: Int,
                  var roomNumber: Int,
                  var bathroomNumber: Int,
                  var bedRoomNumber: Int,
                  var estateStatute: String,
                  var entryDate: Date,
                  var soldDate: Date,
                  var estateAgent: String)




package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "estate_table")
data class Estate(@PrimaryKey @ColumnInfo(name = "type") val estateType:String, val price: Int, val borough: String)




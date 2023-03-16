package com.openclassrooms.realestatemanager

enum class EstateType {
        SELECT,
        FLAT,
        VILLA,
        LOFT;

    override fun toString(): String {
        return when (this) {
            SELECT -> "Estate Type"
            FLAT -> "Flat"
            VILLA -> "Villa"
            LOFT -> "Loft"
        }
    }
}



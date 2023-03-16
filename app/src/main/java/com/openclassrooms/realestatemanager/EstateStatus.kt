package com.openclassrooms.realestatemanager

enum class EstateStatus {
    TO_SALE,
    SOLD,
    UNKNOWN;

    override fun toString(): String {
        return when (this) {
            TO_SALE -> "For sale"
            SOLD -> "Sold"
            UNKNOWN -> "Estate Status"
        }

    }
}
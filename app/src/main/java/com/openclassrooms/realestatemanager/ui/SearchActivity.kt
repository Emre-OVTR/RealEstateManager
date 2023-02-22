package com.openclassrooms.realestatemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.openclassrooms.realestatemanager.EstateStatus
import com.openclassrooms.realestatemanager.EstateType
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var activitySearchBinding: ActivitySearchBinding
    private lateinit var typeSpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private var listOfTypes = EstateType.values()
    private var listOfStatus = EstateStatus.values()
    // Lire rappel fonctionnement class Enums en détails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)
        val view = activitySearchBinding.root
        setContentView(view)
        configureSpinners()

    }

    fun configureSpinners(){
        typeSpinner = activitySearchBinding.estateTypeSpinner
        val typeArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfTypes)
        typeSpinner.adapter = typeArrayAdapter

        statusSpinner = activitySearchBinding.estateStatusSpinner
        val statusArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfStatus)
        statusSpinner.adapter = statusArrayAdapter
    }
}
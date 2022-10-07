package com.openclassrooms.realestatemanager.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate

class EstateDetails : AppCompatActivity() {


    @BindView((R.id.detail_fragment_surface))
    lateinit var textSurface : TextView



    lateinit var estate: Estate.EstateEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estate_details)
        ButterKnife.bind(this)

        estate = intent.getSerializableExtra(ESTATE) as Estate.EstateEntity

        textSurface.text = estate.estateType

    }

    companion object{
        const val ESTATE = "ESTATE"
    }
}













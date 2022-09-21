package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayoutStates
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val estateViewModel: EstateViewModel by viewModels {
        EstateViewModelFactory((application as EstatesApplication).repository)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = EstateListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        estateViewModel.allEstates.observe(this, Observer { estates ->
            estates?.let { adapter.submitList(it) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_appbar, menu)
        return super.onCreateOptionsMenu(menu)
    }


}
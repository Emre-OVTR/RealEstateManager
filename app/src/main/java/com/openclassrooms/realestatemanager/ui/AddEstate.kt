package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.EstatesApplication
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.view.EstateViewModel
import com.openclassrooms.realestatemanager.view.EstateViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*


class AddEstate : AppCompatActivity() {

    @BindView((R.id.add_activity_estate))
    lateinit var txtEstateType: EditText
    @BindView((R.id.add_activity_price))
    lateinit var txtPrice: EditText
    @BindView((R.id.add_activity_surface))
    lateinit var txtSurface: EditText
    @BindView((R.id.add_activity_room_number))
    lateinit var txtRoomNumber: EditText
    @BindView((R.id.add_activity_bathroom_number))
    lateinit var txtBathroomNumber: EditText
    @BindView((R.id.add_activity_bedroom_number))
    lateinit var txtBedroomNumber: EditText
    @BindView((R.id.add_activity_address))
    lateinit var txtAddress: EditText
    @BindView((R.id.add_activity_sector_address))
    lateinit var txtSector: EditText
    @BindView((R.id.add_activity_save))
    lateinit var fab : FloatingActionButton


    private val estateViewModel: EstateViewModel by viewModels {
        EstateViewModelFactory((application as EstatesApplication).repository)

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estate)
        ButterKnife.bind(this)

        fab.setOnClickListener {
            var estate = Estate( 0,
                Integer.parseInt(txtPrice.text.toString()),
                txtEstateType.text.toString(),
                txtSector.text.toString(),
                Integer.parseInt(txtSurface.text.toString()),
                Integer.parseInt(txtRoomNumber.text.toString()),
                Integer.parseInt(txtBathroomNumber.text.toString()),
                Integer.parseInt(txtBedroomNumber.text.toString()),
                txtAddress.text.toString()
            )
            estateViewModel.insert(estate)
            finish()
        }

    }
}
package com.openclassrooms.realestatemanager.ui

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.EstatesApplication
import com.openclassrooms.realestatemanager.ImageRecyclerViewAdapter
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.view.EstateViewModel
import com.openclassrooms.realestatemanager.view.EstateViewModelFactory
import java.io.File


class AddEstateActivity : AppCompatActivity() {

    private val estateViewModel: EstateViewModel by viewModels {
        EstateViewModelFactory(((application as EstatesApplication).repository), ((application as EstatesApplication).imageRepository))
    }


    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let {
                selectedImageUri.add(it.toString())
                adapter.addSelectedImages(selectedImageUri)
            }

        }
    }

    private val selectImagesActivityResult = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                selectedImageUri.add(it.toString())
                adapter.addSelectedImages(selectedImageUri)
            }
        }




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
    lateinit var saveBtn : FloatingActionButton
    @BindView((R.id.add_activity_choose_pic))
    lateinit var choosePicBtn : Button
    @BindView((R.id.add_activity_take_pic))
    lateinit var takePicBtn : Button

    lateinit var estate: Estate.EstateEntity
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageRecyclerViewAdapter
    private var latestTmpUri: Uri? = null
    private var selectedImageUri = mutableListOf<String>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estate)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        ButterKnife.bind(this)
        this.configureRecyclerView()
        this.setOnClickListeners()
    }

    private fun setOnClickListeners(){
        takePicBtn.setOnClickListener { takeImage() }
        choosePicBtn.setOnClickListener { chooseImage() }
        saveBtn.setOnClickListener{ addNewEstate() }

    }

    private fun configureRecyclerView(){
        recyclerView = findViewById(R.id.add_activity_recycler_view)
        adapter = ImageRecyclerViewAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun addNewEstate(){
        val estate = Estate.EstateEntity(
            price = Integer.parseInt(txtPrice.text.toString()),
            estateType = txtEstateType.text.toString(),
            borough = txtSector.text.toString(),
            surface = Integer.parseInt(txtSurface.text.toString()),
            roomNumber = Integer.parseInt(txtRoomNumber.text.toString()),
            bathroomNumber = Integer.parseInt(txtBathroomNumber.text.toString()),
            bedRoomNumber = Integer.parseInt(txtBedroomNumber.text.toString()),
            address = txtAddress.text.toString()

        )
        estateViewModel.insert(estate, selectedImageUri)
        finish()

    }

    private fun takeImage(){
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri =uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun chooseImage(){
        selectImagesActivityResult.launch(arrayOf("image/*"))
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }
}
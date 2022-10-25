package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
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
import com.openclassrooms.realestatemanager.view.EstateViewModel
import com.openclassrooms.realestatemanager.view.EstateViewModelFactory
import org.apache.commons.io.FileUtils.copyInputStreamToFile
import java.io.File


class AddEstateActivity : AppCompatActivity() {

    private val estateViewModel: EstateViewModel by viewModels {
        EstateViewModelFactory((application as EstatesApplication).repository)

    }

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSucces ->
        if (isSucces) {
            latestTmpUri?.let {
                selectedImageUri.add(it)
                adapter.addSelectedImages(selectedImageUri)
            }

        }
    }

    val selectImagesActivityResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri.add(it)
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
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ImageRecyclerViewAdapter
    private var latestTmpUri: Uri? = null
    var selectedImageUri = mutableListOf<Uri>()



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
        var estate = Estate.EstateEntity( 0,
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

    private fun takeImage(){
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri =uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun chooseImage(){
        selectImagesActivityResult.launch("image/*")
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }
}
package com.openclassrooms.realestatemanager.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.EstateType
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.view.EstateViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.LocalDate


@AndroidEntryPoint
class AddEstateActivity : AppCompatActivity() {

    private val estateViewModel: EstateViewModel by viewModels()


    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let {
                    if(selectedImageUri.size == 0){
                        selectedImageUri.add(Image(imageUri = it.toString(), estateId = idEstate  ))

                    } else {
                        imageToInsert.add(Image(imageUri = it.toString(), estateId = intent.getLongExtra(ESTATE1, -1L)))
                        selectedImageUri.addAll(imageToInsert)
                    }
                    adapter.addSelectedImages(selectedImageUri)
                }

            }
        }

    private val selectImagesActivityResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                //JE Convertis l'uri reçu en bitmap puis reconvertis le bitmap en uri pour acceder à l'uri sans nécessiter de permission et pouvoir réutiliser cette uri depuis n'importe ou
                val bit: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                val tempUri: Uri = getImageUriFromBitmap(applicationContext, bit)
                if (selectedImageUri.size == 0){
                    selectedImageUri.add(Image(imageUri = tempUri.toString(), estateId = idEstate))
                } else{
                    imageToInsert.add(Image(imageUri = it.toString(), estateId = intent.getLongExtra(ESTATE1, -1L)))
                    selectedImageUri.addAll(imageToInsert)
                }

                adapter.addSelectedImages(selectedImageUri)
            }
        }

    private fun getImageUriFromBitmap(context: Context?, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context!!.contentResolver, bitmap, "File", null)
        return Uri.parse(path.toString())

    }

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
    @BindView((R.id.add_activity_desc))
    lateinit var txtDescription: EditText
    @BindView((R.id.add_activity_address))
    lateinit var txtAddress: TextView
    @BindView((R.id.add_activity_save))
    lateinit var saveBtn: FloatingActionButton
    @BindView((R.id.add_activity_choose_pic))
    lateinit var choosePicBtn: Button
    @BindView((R.id.add_activity_take_pic))
    lateinit var takePicBtn: Button
    @BindView((R.id.nearby_parks))
    lateinit var parkCheckBox: CheckBox
    @BindView((R.id.nearby_highway))
    lateinit var hwCheckBox : CheckBox
    @BindView((R.id.nearby_schools))
    lateinit var schoolsCheckBox: CheckBox
    @BindView((R.id.nearby_shops))
    lateinit var shopsCheckBox: CheckBox
    @BindView((R.id.add_activity_check_sold))
    lateinit var soldCheckBox: CheckBox


    private lateinit var adapter: ImageRecyclerViewAdapter
    private var latestTmpUri: Uri? = null
    private var selectedImageUri = mutableListOf<Image>()
    private lateinit var spinner: Spinner
    private var listOfItems = EstateType.values()
    private var locationList = mutableListOf<LatLng>()
    private var imageToDelete = mutableListOf<Image>()
    private var imageToInsert = mutableListOf<Image>()
    private var idEstate: Long? = null

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.Notification"
    private val description = "Test Notification"






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estate)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        ButterKnife.bind(this)
        retrieveEstateId()
        this.configureRecyclerView()
        this.setOnClickListeners()
        // Je créer le spinner

        spinner = findViewById(R.id.estateType_spinner)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfItems)
        spinner.adapter = arrayAdapter

            val apiKey: String = getString(R.string.api_key)
            if (!Places.isInitialized()) {
                Places.initialize(applicationContext, apiKey)
            }

            val autocompleteSupportFragment1 =
                supportFragmentManager.findFragmentById(R.id.autocomplete_fragment1) as AutocompleteSupportFragment?
            autocompleteSupportFragment1!!.setTypeFilter(TypeFilter.ADDRESS)
            autocompleteSupportFragment1.setPlaceFields(
                listOf(
                    Place.Field.NAME,
                    Place.Field.ADDRESS,
                    Place.Field.PHONE_NUMBER,
                    Place.Field.LAT_LNG,
                    Place.Field.OPENING_HOURS,
                    Place.Field.RATING,
                    Place.Field.USER_RATINGS_TOTAL
                )
            )

            autocompleteSupportFragment1.setOnPlaceSelectedListener(object :
                PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    val textView = findViewById<TextView>(R.id.add_activity_address)

                    val name = place.name
                    val address = place.address
                    val phone = place.phoneNumber
                    val latLng = place.latLng
                    val latitude = latLng?.latitude
                    val longitude = latLng?.longitude

                    val isOpenStatus: String = if (place.isOpen == true) {
                        "Open"
                    } else {
                        "Closed"
                    }

                    val rating = place.rating
                    val userRatings = place.userRatingsTotal

                    textView.text = address
                    if (locationList.isEmpty()){
                        locationList.add(latLng)
                    } else {
                        locationList.replaceAll { latLng }
                    }


                }

                override fun onError(status: Status) {
                    Toast.makeText(applicationContext, "Some error occrurred", Toast.LENGTH_SHORT)
                        .show()
                }
            })


    }


    companion object{
        const val ESTATE1 = "ESTATE"

    }

    private fun setOnClickListeners() {
        takePicBtn.setOnClickListener { takeImage() }
        choosePicBtn.setOnClickListener { chooseImage() }
        saveBtn.setOnClickListener {
            when{
                txtPrice.text.isNullOrBlank() -> Toast.makeText(applicationContext,"Please write the price of this Estate", Toast.LENGTH_LONG).show()
                txtRoomNumber.text.isNullOrBlank() -> Toast.makeText(applicationContext,"Please enter number of rooms in this Estate", Toast.LENGTH_LONG).show()
                txtBedroomNumber.text.isNullOrBlank() -> Toast.makeText(applicationContext,"Please enter number of Bedrooms in this Estate", Toast.LENGTH_LONG).show()
                txtBathroomNumber.text.isNullOrBlank() -> Toast.makeText(applicationContext,"Please enter number of Bathrooms in this Estate", Toast.LENGTH_LONG).show()
                txtSurface.text.isNullOrBlank()  -> Toast.makeText(applicationContext,"Please enter surface of this Estate", Toast.LENGTH_LONG).show()
                txtDescription.text.isNullOrBlank() -> Toast.makeText(applicationContext,"Please write description for this Estate", Toast.LENGTH_LONG).show()
                txtAddress.text.isNullOrBlank() -> Toast.makeText(applicationContext,"Please select an address for this Estate", Toast.LENGTH_LONG).show()
                Integer.parseInt(txtRoomNumber.text.toString()) < Integer.parseInt(txtBedroomNumber.text.toString()) -> Toast.makeText(applicationContext,"Room number should be higher than bedroom number", Toast.LENGTH_LONG).show()
                Integer.parseInt(txtRoomNumber.text.toString()) < Integer.parseInt(txtBathroomNumber.text.toString()) -> Toast.makeText(applicationContext, "Room Number should be higher than bathroom number", Toast.LENGTH_LONG).show()
                Integer.parseInt(txtBedroomNumber.text.toString()) < Integer.parseInt(txtBathroomNumber.text.toString()) -> Toast.makeText(applicationContext, "Bedroom Number should be higher than bathroom number", Toast.LENGTH_LONG).show()
                selectedImageUri.size == 0 -> Toast.makeText(applicationContext, "Please add at least one picture", Toast.LENGTH_LONG).show()
            else-> addNewEstate() }}
    }

    private fun configureRecyclerView() {
   //     recyclerView = findViewById(R.id.add_activity_recycler_view)
   //     adapter = ImageRecyclerViewAdapter()
    //    recyclerView.adapter = adapter
     //   recyclerView.layoutManager =
       //     LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val viewPager = findViewById<ViewPager2>(R.id.add_activity_recycler_view)
        viewPager.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        adapter = ImageRecyclerViewAdapter(this::onItemDelete)
        viewPager.adapter = adapter

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }

        viewPager.setPageTransformer(compositePageTransformer)
    }

    private fun onItemDelete(imageUri: Image){
        //estateViewModel.deleteImage(imageUri)
        imageToDelete.add(imageUri)
    }

    private fun addNewEstate() {
        val estate = Estate(
            price = Integer.parseInt(txtPrice.text.toString()),
            estateTypePosition = spinner.selectedItemPosition,
            surface = Integer.parseInt(txtSurface.text.toString()),
            roomNumber = Integer.parseInt(txtRoomNumber.text.toString()),
            bathroomNumber = Integer.parseInt(txtBathroomNumber.text.toString()),
            bedRoomNumber = Integer.parseInt(txtBedroomNumber.text.toString()),
            address = txtAddress.text.toString(),
            latitude = locationList[0].latitude,
            longitude = locationList[0].longitude,
            description = txtDescription.text.toString(),
            isNearParks = parkCheckBox.isChecked,
            isNearHighway =hwCheckBox.isChecked,
            isNearSchools = schoolsCheckBox.isChecked,
            isNearShops = shopsCheckBox.isChecked,
            creationDate = LocalDate.now(),
            estateTypeName = spinner.selectedItem.toString(),
            isSold = soldCheckBox.isChecked
            //coordinate = locationList[0]

        )
        estateViewModel.insert(estate, selectedImageUri)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContentText("Your new Estate is added with success !")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))

        } else {

            builder = Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))

        }
        notificationManager.notify(1234, builder.build())

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
        return FileProvider.getUriForFile(applicationContext, "${com.openclassrooms.realestatemanager.BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }


    private fun bind(estate: Long, image: MutableList<Image>) {


        estateViewModel.getEstateById(estate).observe(this){ estateById ->
            estateById.let {    txtBathroomNumber.setText(estateById.bathroomNumber.toString())
                txtBedroomNumber.setText(estateById.bedRoomNumber.toString())
                txtDescription.setText(estateById.description)
                txtPrice.setText(estateById.price.toString())
                txtRoomNumber.setText(estateById.roomNumber.toString())
                txtAddress.text = estateById.address
                txtSurface.setText(estateById.surface.toString())
                locationList.add(LatLng(estateById.latitude, estateById.longitude))


                if(estateById.isNearParks) parkCheckBox.isChecked = true
                if(estateById.isNearHighway) hwCheckBox.isChecked = true
                if(estateById.isNearSchools) schoolsCheckBox.isChecked = true
                if (estateById.isNearShops) shopsCheckBox.isChecked = true
                if(estateById.isSold) soldCheckBox.isChecked = true

                for (i in image){
                    selectedImageUri.addAll(listOf(i))
                    adapter.addSelectedImages(selectedImageUri)
                }
                //val imageUri : String = image[].imageUri
               // selectedImageUri.add(imageUri)
                //adapter.addSelectedImages(selectedImageUri)
                spinner.setSelection(estateById.estateTypePosition)


            }

        }

        takePicBtn.setOnClickListener { takeImage() }
        choosePicBtn.setOnClickListener { chooseImage() }
        saveBtn.setOnClickListener {
            updateEstate()
            finish()}

    }

    private fun retrieveEstateId() {
        val estateId = intent.getLongExtra(ESTATE1, -1L)
        val listImage: MutableList<Image> = ArrayList()
        // val image = estate?.id?.let { Image(estateId = it, imageUri = ) }
        estateViewModel.getImages(estateId).observe(this) { imagesById ->
            imagesById.let { listImage.addAll(imagesById) }

            if (estateId != -1L) {
                bind(estateId, listImage)
            }

        }


    }

    private fun updateEstate(){
        val updatedEstate = Estate(id = intent.getLongExtra(ESTATE1, -1L),
            price = Integer.parseInt(txtPrice.text.toString()),
            estateTypePosition = spinner.selectedItemPosition,
            surface = Integer.parseInt(txtSurface.text.toString()),
            roomNumber = Integer.parseInt(txtRoomNumber.text.toString()),
            bathroomNumber = Integer.parseInt(txtBathroomNumber.text.toString()),
            bedRoomNumber = Integer.parseInt(txtBedroomNumber.text.toString()),
            address = txtAddress.text.toString(),
            latitude = locationList[0].latitude,
            longitude = locationList[0].longitude,
            description = txtDescription.text.toString(),
            isNearParks = parkCheckBox.isChecked,
            isNearHighway =hwCheckBox.isChecked,
            isNearSchools = schoolsCheckBox.isChecked,
            isNearShops = shopsCheckBox.isChecked,
            creationDate = LocalDate.now(),
            estateTypeName = spinner.selectedItem.toString(),
            isSold = soldCheckBox.isChecked)

        estateViewModel.updateEstate(updatedEstate, imageToDelete, imageToInsert)
        // coordinate = locationList[0])
    }


    // conditionner avec should add
    // regarder 3eme liste itemtoadd



}

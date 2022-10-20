package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.EstatesApplication
import com.openclassrooms.realestatemanager.ImageRecyclerViewAdapter
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.view.EstateViewModel
import com.openclassrooms.realestatemanager.view.EstateViewModelFactory
import org.apache.commons.io.FileUtils.copyInputStreamToFile
import java.io.File


class AddEstateActivity : AppCompatActivity() {

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
    @BindView((R.id.add_activity_choose_pic))
    lateinit var btn : Button

    lateinit var estate: Estate.EstateEntity
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ImageRecyclerViewAdapter
    var selectedPaths = mutableListOf<String>()

    private val estateViewModel: EstateViewModel by viewModels {
        EstateViewModelFactory((application as EstatesApplication).repository)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estate)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        ButterKnife.bind(this)


        fab.setOnClickListener {
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

        recyclerView = findViewById(R.id.add_activity_recycler_view)
        adapter = ImageRecyclerViewAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val selectImagesActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    //If multiple image selected
                    if (data?.clipData != null) {
                        val count = data.clipData?.itemCount ?: 0

                        for (i in 0 until count) {
                            val imageUri: Uri? = data.clipData?.getItemAt(i)?.uri
                            val file = getImageFromUri(imageUri)
                            file?.let {
                                selectedPaths.add(it.absolutePath)
                            }
                        }
                        adapter.addSelectedImages(selectedPaths)
                    }
                    //If single image selected
                    else if (data?.data != null) {
                        val imageUri: Uri? = data.data
                        val file = getImageFromUri(imageUri)
                        file?.let {
                            selectedPaths.add(it.absolutePath)
                        }
                        adapter.addSelectedImages(selectedPaths)
                    }
                }
            }


        btn.setOnClickListener {
            val intent = Intent(ACTION_GET_CONTENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "*/*"
            selectImagesActivityResult.launch(intent)
        }
        try {
            deleteTempFiles()
        } catch (e: Exception) {

        }

        

    }


    private fun getImageFromUri(imageUri: Uri?) : File? {
        imageUri?.let { uri ->
            val mimeType = getMimeType(this, uri)
            mimeType?.let {
                val file = createTmpFileFromUri(this, imageUri,"temp_image", ".$it")
                file?.let { Log.d("image Url = ", file.absolutePath) }
                return file
            }
        }
        return null
    }

    private fun getMimeType(context: Context, uri: Uri): String? {
        //Check uri format to avoid null
        val extension: String? = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }

    private fun createTmpFileFromUri(context: Context, uri: Uri, fileName: String, mimeType: String): File? {
        return try {
            val stream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile(fileName, mimeType,cacheDir)
            copyInputStreamToFile(stream, file)
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun deleteTempFiles(file: File = cacheDir): Boolean {
        if (file.isDirectory) {
            val files = file.listFiles()
            if (files != null) {
                for (f in files) {
                    if (f.isDirectory) {
                        deleteTempFiles(f)
                    } else {
                        f.delete()
                    }
                }
            }
        }
        return file.delete()
    }
}
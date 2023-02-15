package com.openclassrooms.realestatemanager.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Image


class ImageRecyclerViewAdapter(private val clickListener : (imageUri: Image ) -> Unit): RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder>() {

    private var selectedImageUri = mutableListOf<Image>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_recyclerview_item, parent, false)
        view.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
       val imageUri = selectedImageUri[position]
        holder.picture.setImageURI(Uri.parse(imageUri.imageUri))
        holder.button.setOnClickListener {
            clickListener(selectedImageUri[position])
            selectedImageUri.remove(imageUri)
            addSelectedImages(selectedImageUri)
        }
    }


    override fun getItemCount(): Int {
        return selectedImageUri.size
    }


    fun addSelectedImages(images: MutableList<Image>){
        this.selectedImageUri = images
        notifyDataSetChanged()
    }


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val picture : ImageView = itemView.findViewById(R.id.imageView)
        val button : ImageButton = itemView.findViewById(R.id.button1)
    }

    //passer des images au lieu des Uri
    //recuperer(get) uri de chaque image
    //creer fonction ds addestate qui va prendre une image en parametre que je vais passer en lambda dans l adapter(constructeur)
    // utiliser lambda dans le viewholder
}
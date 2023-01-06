package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R

class ImageRecyclerViewAdapter: RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder>() {

    private var selectedImageUri = listOf<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_recyclerview_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
       val imageUri = selectedImageUri[position]
        holder.picture.setImageURI(Uri.parse(imageUri))
    }


    override fun getItemCount(): Int {
        return selectedImageUri.size

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSelectedImages(images: List<String>) {
        this.selectedImageUri = images
        notifyDataSetChanged()
    }


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picture : ImageView = itemView.findViewById(R.id.imageView)

    }
}
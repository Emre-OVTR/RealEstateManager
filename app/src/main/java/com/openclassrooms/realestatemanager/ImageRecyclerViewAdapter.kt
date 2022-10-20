package com.openclassrooms.realestatemanager

import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R2.attr.uri
import com.openclassrooms.realestatemanager.ui.EstateListAdapter
import kotlinx.android.synthetic.main.image_recyclerview_item.view.*

class ImageRecyclerViewAdapter: RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder>() {

    var selectedImagePath = listOf<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_recyclerview_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
       val imagePath = selectedImagePath[position]
        holder.picture.setImageBitmap(BitmapFactory.decodeFile(imagePath))
    }



    override fun getItemCount(): Int {
        return selectedImagePath.size

    }

    fun addSelectedImages(images: List<String>) {
        this.selectedImagePath = images
        notifyDataSetChanged()
    }


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picture : ImageView = itemView.findViewById(R.id.imageView)

    }
}
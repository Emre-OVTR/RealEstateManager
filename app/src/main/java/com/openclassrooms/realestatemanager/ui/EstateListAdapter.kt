package com.openclassrooms.realestatemanager.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import kotlin.coroutines.coroutineContext

class EstateListAdapter(private val onClickListener: OnClickListener ) : ListAdapter<Estate, EstateListAdapter.EstateViewHolder>(MyDiffUtil()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        return EstateViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        val estate = getItem(position)
        holder.bind(estate)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(estate)
        }

    }

    class EstateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val estateItemView: TextView = itemView.findViewById(R.id.text_view)
        private val estateItemPrice: TextView = itemView.findViewById(R.id.list_item_price)
        private val estateItemBorough: TextView = itemView.findViewById(R.id.list_item_city)

        fun bind(estate: Estate) {
            estateItemView.text = estate.estateType
            estateItemPrice.text = estate.price.toString()
            estateItemBorough.text = estate.borough


        }


        companion object {
            fun create(parent: ViewGroup): EstateViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return EstateViewHolder(view)
            }
        }


    }

    class MyDiffUtil : DiffUtil.ItemCallback<Estate>() {
        override fun areItemsTheSame(oldItem: Estate, newItem: Estate): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Estate, newItem: Estate): Boolean {
            return oldItem.id == newItem.id
        }
    }

    fun getEstateDetails(position: Int): Estate {
      return getItem(position)
    }


    class OnClickListener(val clickListener: (estate: Estate) -> Unit) {
        fun onClick(estate: Estate) = clickListener(estate)
    }


}
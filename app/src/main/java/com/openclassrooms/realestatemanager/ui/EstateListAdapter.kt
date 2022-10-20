package com.openclassrooms.realestatemanager.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate

class EstateListAdapter(private val clickListener: (estate: Estate.EstateEntity) -> Unit ) : ListAdapter<Estate.EstateEntity, EstateListAdapter.EstateViewHolder>(MyDiffUtil()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        return EstateViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        val estate = getItem(position)
        holder.bind(estate, clickListener)

    }

    class EstateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val estateItemView: TextView = itemView.findViewById(R.id.text_view)
        private val estateItemPrice: TextView = itemView.findViewById(R.id.list_item_price)
        private val estateItemBorough: TextView = itemView.findViewById(R.id.list_item_city)


        fun bind(estate: Estate.EstateEntity, clickListener: (estate: Estate.EstateEntity) -> Unit) {
            estateItemView.text = estate.estateType
            estateItemPrice.text = estate.price.toString()
            estateItemBorough.text = estate.borough

            itemView.setOnClickListener{
                clickListener(estate)
            }
        }






        companion object {
            fun create(parent: ViewGroup): EstateViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return EstateViewHolder(view)
            }
        }


    }

    class MyDiffUtil : DiffUtil.ItemCallback<Estate.EstateEntity>() {
        override fun areItemsTheSame(oldItem: Estate.EstateEntity, newItem: Estate.EstateEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Estate.EstateEntity, newItem: Estate.EstateEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }




}
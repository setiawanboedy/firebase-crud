package com.example.firebasecrud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasecrudclient.databinding.ProductItemBinding
import com.example.firebasecrudclient.model.ProductModel

class ProductAdapter: ListAdapter<ProductModel, ProductAdapter.ViewHolder>(differCallback) {
    companion object {
        val differCallback = object : DiffUtil.ItemCallback<ProductModel>(){
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean =
                oldItem == newItem

        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    inner class ViewHolder(private val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductModel){
            with(binding){

                nameText.text = data.name
                priceText.text = data.price.toString()
                descriptionText.text = data.description
                createDateText.text = data.create_date?.toDate().toString()
                updateDateText.text = data.update_date?.toDate().toString()

            }
        }
    }

}
package com.example.firebasecrud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasecrud.databinding.ProductItemBinding
import com.example.firebasecrud.model.ProductModel

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
        if (currentItem != null) holder.bind(currentItem, listener, listenerDel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    inner class ViewHolder(private val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductModel, listener: ((ProductModel) -> Unit)?, listenerDel: ((ProductModel) -> Unit)?){
            with(binding){

                nameText.text = data.name
                priceText.text = data.price.toString()
                descriptionText.text = data.description
                createDateText.text = data.create_date?.toDate().toString()
                updateDateText.text = data.update_date?.toDate().toString()
                itemView.setOnClickListener {
                    listener?.let {
                        listener(data)
                    }
                }
                delete.setOnClickListener {
                    listenerDel?.let {
                        listenerDel(data)
                    }
                }
            }
        }
    }

    private var listener : ((ProductModel) -> Unit)? = null
    fun setOnItemClick(listener: (ProductModel) -> Unit){
        this.listener = listener
    }
    private var listenerDel : ((ProductModel) -> Unit)? = null
    fun setOnDelClick(listener: (ProductModel) -> Unit){
        this.listenerDel = listener
    }

}
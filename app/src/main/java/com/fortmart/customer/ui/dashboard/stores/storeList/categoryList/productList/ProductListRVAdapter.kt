package com.fortmart.customer.ui.dashboard.stores.storeList.categoryList.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fortmart.customer.R
import com.fortmart.customer.databinding.ItemProductBinding
import com.fortmart.customer.model.other.ProductDetails

class ProductListRVAdapter(private val mData: Array<ProductDetails>): RecyclerView.Adapter<ProductListRVAdapter.ViewHolder>(){

    var itemClickListener: ((Int, View)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.image.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_apple))
            binding.productDetails= mData[position]
            binding.add.setOnClickListener{
                itemClickListener?.invoke(position, it)
            }
        }
    }

}
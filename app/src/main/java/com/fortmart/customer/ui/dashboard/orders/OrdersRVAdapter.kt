package com.fortmart.customer.ui.dashboard.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fortmart.customer.databinding.ItemCategoryBinding
import com.fortmart.customer.databinding.ItemOrderBinding
import com.fortmart.customer.model.other.ImageMenu
import com.fortmart.customer.model.other.OrderDetails

class OrdersRVAdapter(private val mData: Array<OrderDetails>): RecyclerView.Adapter<OrdersRVAdapter.ViewHolder>(){

    var itemClickListener: ((Int, View)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(private val binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.productDetails = mData[position]
            binding.executePendingBindings()
        }
    }

}
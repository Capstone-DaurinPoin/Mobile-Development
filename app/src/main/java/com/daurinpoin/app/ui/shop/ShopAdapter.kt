package com.daurinpoin.app.ui.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daurinpoin.app.R
import com.daurinpoin.app.response.ShopItem

class ShopAdapter : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    private val items = mutableListOf<ShopItem>()

    class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.titleCart)
        val itemPrice: TextView = itemView.findViewById(R.id.priceCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.itemPrice.text = item.price
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: ShopItem) {
        items.add(item)
        notifyDataSetChanged()
    }
}

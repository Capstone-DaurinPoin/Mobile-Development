package com.daurinpoin.app.ui.rewards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daurinpoin.app.R
import com.daurinpoin.app.response.DataItem

interface CartListener {
    fun onAddToCartClick(reward: DataItem?)
}

class RewardAdapter(
    private var rewards: List<DataItem?>, private val cartListener: CartListener
) : RecyclerView.Adapter<RewardAdapter.RewardViewHolder>() {

    class RewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleReward)
        val price: TextView = itemView.findViewById(R.id.priceRewards)
        val btnPlus: Button = itemView.findViewById(R.id.plusButtonRewards)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reward, parent, false)
        return RewardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val reward = rewards[position]

        holder.title.text = reward?.name
        holder.price.text = reward?.price.toString()

        holder.btnPlus.setOnClickListener {
            cartListener.onAddToCartClick(reward)
        }
    }

    fun updateData(newRewards: List<DataItem?>) {
        rewards = newRewards
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return rewards.size
    }
}

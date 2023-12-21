package com.daurinpoin.app.ui.rewards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daurinpoin.app.R
import com.daurinpoin.app.response.CartResult
import com.daurinpoin.app.response.DataItem
import com.daurinpoin.app.response.RewardsResponse
import com.daurinpoin.app.service.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RewardsActivity : AppCompatActivity(), CartListener {

    private lateinit var recyclerViewRewards: RecyclerView
    private lateinit var rewardAdapter: RewardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)

        recyclerViewRewards = findViewById(R.id.recyclerViewRewards)
        recyclerViewRewards.layoutManager = LinearLayoutManager(this)
        rewardAdapter = RewardAdapter(emptyList(),this)
        recyclerViewRewards.adapter = rewardAdapter

        // Ambil data rewards dari API dan tampilkan di RecyclerView
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val rewardsResponse: RewardsResponse = ApiClient.apiService.getRewards()
                val rewards = rewardsResponse.data ?: emptyList()
                rewardAdapter.updateData(rewards)
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

    override fun onAddToCartClick(reward: DataItem?) {
        if (reward != null) {
            val cartItem = CartResult(
                shopId = 0,
//                idUser = reward.id_user ?: 0,
                idReward = reward.idReward ?: 0,
                jumlahProduct = 1,
                status = "success",
                createdAt = reward.createdAt ?: "",
                updatedAt = reward.updatedAt ?: ""
            )

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val response = ApiClient.apiService.addToCart(cartItem)
                    // Handle respons sesuai kebutuhan
                } catch (e: Exception) {
                    // Handle error
                    e.printStackTrace()
                }
            }
        }
    }
}
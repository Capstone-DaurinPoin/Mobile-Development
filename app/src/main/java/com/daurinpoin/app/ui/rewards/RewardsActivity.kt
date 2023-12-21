package com.daurinpoin.app.ui.rewards

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daurinpoin.app.R
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
        rewardAdapter = RewardAdapter(emptyList(), this)
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
        // Implement logic to add the reward to the cart
        // ...
        // Example: send the selected item's price back to HomeFragment
        val selectedItemPrice = reward?.price ?: 0

        val sharedPreferences =
            applicationContext.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val currentBalance = sharedPreferences.getInt("point", 0)

        if (currentBalance >= selectedItemPrice) {
            // Sufficient balance, proceed with the purchase
            Toast.makeText(this,"Success Buy", Toast.LENGTH_SHORT).show()
            val resultIntent = Intent()
            resultIntent.putExtra("selectedItemPrice", selectedItemPrice)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            // Insufficient balance, show a Toast
            val insufficientToast = Toast.makeText(
                this, "Insufficient Point. Current balance: $currentBalance", Toast.LENGTH_SHORT
            )
            insufficientToast.show()
        }
    }
}
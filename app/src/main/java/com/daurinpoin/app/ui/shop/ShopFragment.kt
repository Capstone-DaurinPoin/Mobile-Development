package com.daurinpoin.app.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daurinpoin.app.R
import com.daurinpoin.app.response.DataItem
import com.daurinpoin.app.response.ShopItem

class ShopFragment : Fragment() {

    private lateinit var recyclerViewShop: RecyclerView
    private lateinit var shopAdapter: ShopAdapter

    companion object {
        private const val ARG_REWARD = "arg_reward"

        fun newInstance(reward: DataItem? = null): ShopFragment {
            val fragment = ShopFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewShop = view.findViewById(R.id.recyclerCart)
        recyclerViewShop.layoutManager = LinearLayoutManager(requireContext())
        shopAdapter = ShopAdapter()
        recyclerViewShop.adapter = shopAdapter

        // Retrieve data from arguments
        val reward = arguments?.get(ARG_REWARD) as? DataItem

        // Dummy implementation: Add the selected item to the shop list
        if (reward != null) {
            val shopItem = ShopItem(reward.name, reward.price.toString())
            shopAdapter.addItem(shopItem)
        }
    }
}




package com.daurinpoin.app.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daurinpoin.app.R
import com.daurinpoin.app.service.ApiClient
import com.daurinpoin.app.ui.directory.DirectoryActivity
import com.daurinpoin.app.ui.news.NewsAdapter
import com.daurinpoin.app.ui.notification.NotifActivity
import com.daurinpoin.app.ui.rewards.RewardsActivity
import com.daurinpoin.app.ui.scan.ScanActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var btnScan: FrameLayout
    private lateinit var btnShop: FrameLayout
    private lateinit var btnDirectory: FrameLayout
    private lateinit var btnNotif: ImageView
    private lateinit var textName: TextView
    private lateinit var textPoints: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textName = view.findViewById(R.id.textNamaHome)
        textPoints = view.findViewById(R.id.textShowPoin)

        btnScan = view.findViewById(R.id.btn_ScanTrash)
        btnShop = view.findViewById(R.id.btn_shopRewards)
        btnDirectory = view.findViewById(R.id.btn_directoryHome)
        btnNotif = view.findViewById(R.id.btnNotifikasi)


        loadUser()

        btnScan.setOnClickListener {
            val intent = Intent(requireContext(), ScanActivity::class.java)
            startActivity(intent)
        }
        btnShop.setOnClickListener {
            val intent = Intent(requireContext(), RewardsActivity::class.java)
            startActivity(intent)
        }
        btnDirectory.setOnClickListener {
            val intent = Intent(requireContext(), DirectoryActivity::class.java)
            startActivity(intent)
        }
        btnNotif.setOnClickListener {
            val intent = Intent(requireContext(), NotifActivity::class.java)
            startActivity(intent)
        }

        recyclerView = view.findViewById(R.id.recyclerNews)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        newsAdapter = NewsAdapter(requireContext(), emptyList())
        recyclerView.adapter = newsAdapter

        // Fetch news data from the API
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val newsResponse = ApiClient.apiService.getNews()
                val articles = newsResponse.articles ?: emptyList()
                newsAdapter.updateData(articles)
            } catch (e: Exception) {
                // Handle the error (e.g., show a toast)
                e.printStackTrace()
                Toast.makeText(requireContext(), "Failed to fetch news", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun formatNumberWithDots(number: Long): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
        val symbols = (numberFormat as java.text.DecimalFormat).decimalFormatSymbols
        symbols.groupingSeparator = '.'
        numberFormat.decimalFormatSymbols = symbols
        return numberFormat.format(number)
    }

    private fun loadUser() {
        val sharedPreferences =
            requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)

        val name = sharedPreferences.getString("nama", "")
        val points = sharedPreferences.getInt("point", 0)

        val words = name?.split(" ")
        val firstName = words?.firstOrNull()

        val formattedNumber = formatNumberWithDots(points.toLong())

        textName.text = firstName
        textPoints.text = formattedNumber
    }
}

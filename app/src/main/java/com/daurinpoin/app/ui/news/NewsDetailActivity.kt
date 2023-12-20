package com.daurinpoin.app.ui.news

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.daurinpoin.app.R


class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_detail)

        val imageDetail: ImageView = findViewById(R.id.imageDetail)
        val textTitleDetail: TextView = findViewById(R.id.textTitleDetail)
        val textDescriptionDetail: TextView = findViewById(R.id.textDescriptionDetail)
        val textPublishedAt: TextView = findViewById(R.id.textPublishedAt)

        // Retrieve data from the intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val publishedAt = intent.getStringExtra("publishedAt")
        val content = intent.getStringExtra("content")
        val imageUrl = intent.getStringExtra("urlToImage")

        // Set data to the views
        textTitleDetail.text = title
        textDescriptionDetail.text = description
        textPublishedAt.text = publishedAt

        // Load image using Glide
        Glide.with(this).load(imageUrl).placeholder(R.drawable.no_image)
            .error(R.drawable.no_image).into(imageDetail)
    }
}

package com.daurinpoin.app.ui.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daurinpoin.app.R
import com.daurinpoin.app.response.ArticlesItem

class NewsAdapter(private val context: Context, private var articles: List<ArticlesItem?>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.newsTitle)
        val description: TextView = itemView.findViewById(R.id.textDescriptionNews)
        val image: ImageView = itemView.findViewById(R.id.imageNews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]

        holder.title.text = article?.title
        holder.description.text = article?.description

        // Load image only if the URL is not empty
        if (!article?.urlToImage.isNullOrEmpty()) {
            Glide.with(holder.itemView)
                .load(article?.urlToImage)
                .into(holder.image)
        } else {
            // Handle the case where the image URL is empty or null
            holder.image.setImageResource(R.drawable.no_image)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java).apply {
                putExtra("title", article?.title)
                putExtra("description", article?.description)
                putExtra("publishedAt", article?.publishedAt)
                putExtra("content", article?.content)
                putExtra("urlToImage", article?.urlToImage)
            }
            context.startActivity(intent)
        }
    }

    fun updateData(newArticles: List<ArticlesItem?>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}
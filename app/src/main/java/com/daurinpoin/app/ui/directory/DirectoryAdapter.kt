package com.daurinpoin.app.ui.directory

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.daurinpoin.app.R

class DirectoryAdapter(private val items: List<LabelItem>) :
    RecyclerView.Adapter<DirectoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewDirectory)
        val labelTextView: TextView = itemView.findViewById(R.id.textTitleDirectory)
        val timeStampText: TextView = itemView.findViewById(R.id.textSubtitleDirectory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_directory, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]

        // Load image asynchronously using Glide
/*        Glide.with(holder.itemView.context)
            .load(Uri.parse("file://" + currentItem.imagePath))
            .placeholder(R.drawable.no_image) // Placeholder image
            .error(R.drawable.no_image)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    // Image loaded successfully
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("Glide", "Load failed for ${currentItem.imagePath}", e)
                    return true
                }
            })
            .into(holder.imageView)*/

        holder.labelTextView.text = currentItem.label
        holder.timeStampText.text = currentItem.timestamp
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

package com.example.kotlinbackgroundprocessing.ui.photos

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinbackgroundprocessing.R
import com.example.kotlinbackgroundprocessing.app.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_photo.view.*

class PhotosAdapter(private val photos: MutableList<String>) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.list_item_photo))

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    fun updatePhotos(photos: List<String>) {
        this.photos.clear()
        this.photos.addAll(photos)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(photo: String) {
            Picasso.get().load(photo).into(itemView.photo)
        }
    }
}
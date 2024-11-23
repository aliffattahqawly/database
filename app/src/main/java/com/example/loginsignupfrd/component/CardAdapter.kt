package com.example.loginsignupfrd.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginsignupfrd.R
import com.example.loginsignupfrd.model.Item
import com.example.loginsignupfrd.ui.deskripsi.DetailItemActivity


    class CardAdapter(private val items: List<Item>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            return CardViewHolder(view)
        }

        override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
            val item = items[position]
            holder.bind(item)
            // Handle item click
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailItemActivity::class.java)
                intent.putExtra("title", item.name)
                intent.putExtra("description", item.description)
                intent.putExtra("videoUrl", item.videoUrl)
                intent.putExtra("fileUrl", item.fileUrl)
                holder.itemView.context.startActivity(intent)
            }
        }


            override fun getItemCount(): Int {
                return items.size
            }

                inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                    private val imageView: ImageView = itemView.findViewById(R.id.item_image)
                    private val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
                    private val descriptionTextView: TextView = itemView.findViewById(R.id.tvDescription)

                    fun bind(item: Item) {
                        // Set gambar dari URL menggunakan Glide
                        Glide.with(itemView.context)
                            .load(item.imageUrl)
                            .placeholder(R.drawable.placeholder_image) // Placeholder gambar sementara
                            .error(R.drawable.error_image) // Gambar yang ditampilkan jika terjadi error
                            .into(imageView)
                        // Set judul dan deskripsi
                        titleTextView.text = item.name
                        descriptionTextView.text = item.description
                    }
                }
            }
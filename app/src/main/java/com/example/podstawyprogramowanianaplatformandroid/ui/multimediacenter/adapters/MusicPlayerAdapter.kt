package com.example.podstawyprogramowanianaplatformandroid.ui.multimediacenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.podstawyprogramowanianaplatformandroid.R

class MusicPlayerAdapter : RecyclerView.Adapter<MusicPlayerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val musicTitle: TextView = itemView.findViewById(R.id.tv_music_title)
    }

    private val items = ArrayList<Triple<String, Int, Int>>()
    private var onItemClickListener: ((Triple<String, Int, Int>) -> Unit)? = null

    fun setItems(list: List<Triple<String, Int, Int>>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun setonItemClickListener(action: (Triple<String, Int, Int>) -> Unit) {
        onItemClickListener = action
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_music_player, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        with(holder) {
            musicTitle.text = item.first
            itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }
}
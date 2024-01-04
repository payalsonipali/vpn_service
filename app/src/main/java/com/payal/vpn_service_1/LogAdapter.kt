package com.payal.vpn_service_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogAdapter : RecyclerView.Adapter<LogAdapter.UrlViewHolder>() {

    private val urls: MutableList<String> = mutableListOf()

    fun addUrl(url: String) {
        urls.add(url)
        notifyItemInserted(urls.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrlViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return UrlViewHolder(view)
    }

    override fun onBindViewHolder(holder: UrlViewHolder, position: Int) {
        holder.bind(urls[position])
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    class UrlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val urlTextView: TextView = itemView.findViewById(R.id.urlTextView)

        fun bind(url: String) {
            urlTextView.text = url
        }
    }
}

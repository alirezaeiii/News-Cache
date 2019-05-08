package com.sample.android.news.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.android.news.databinding.NewsItemBinding
import com.sample.android.news.domain.Article
import com.sample.android.news.util.layoutInflater

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    /**
     * The Articles that our Adapter will show
     */
    var news: List<Article> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(
            parent.context.layoutInflater,
            parent,
            false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = news.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.also {
            it.article = news[position]
        }
    }

    /**
     * ViewHolder for News items. All work is done by data binding.
     */
    class NewsViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
        }
    }
}
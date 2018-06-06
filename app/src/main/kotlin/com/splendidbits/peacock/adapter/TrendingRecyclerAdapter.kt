package com.splendidbits.peacock.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.splendidbits.peacock.R
import com.splendidbits.peacock.enums.NewsType
import com.splendidbits.peacock.model.NewsItem
import com.splendidbits.peacock.view.AbstractNewsItemHolder
import com.splendidbits.peacock.view.NewsItemArticleHolder
import com.splendidbits.peacock.view.NewsItemFeaturedHolder
import com.splendidbits.peacock.view.NewsItemVideoHolder
import com.squareup.picasso.Picasso

class TrendingRecyclerAdapter(val context: Context, private val picassoBuilder: Picasso.Builder) :
        ListAdapter<NewsItem, AbstractNewsItemHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean =
                    oldItem.itemId == newItem.itemId

            override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractNewsItemHolder {
        when (viewType) {
            NewsType.TYPE_FEATURED.ordinal -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_news_featured, parent, false)
                return NewsItemFeaturedHolder(itemView)
            }
            NewsType.TYPE_VIDEO.ordinal -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_news_video, parent, false)
                return NewsItemVideoHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_news_article, parent, false)
                return NewsItemArticleHolder(itemView)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).itemId.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.ordinal
    }

    override fun onBindViewHolder(viewHolder: AbstractNewsItemHolder, position: Int) {
        val item = getItem(position)
        viewHolder.newsItem = item

        when (viewHolder) {
            is NewsItemFeaturedHolder -> {
                if (item.tease.isNotEmpty()) {
                    picassoBuilder
                            .build()
                            .load(item.tease)
                            .priority(Picasso.Priority.HIGH)
                            .placeholder(R.drawable.tease_rect_placeholder)
                            .fit()
                            .centerCrop()
                            .into(viewHolder.teaseImage)
                }
                viewHolder.headlineText.text = item.headline
                viewHolder.summaryText.text = item.summary
                viewHolder.pillText.text = if (item.breaking)
                    context.getString(R.string.breaking) else context.getString(R.string.breaking)
            }

            is NewsItemArticleHolder -> {
                if (item.tease.isNotEmpty()) {
                    picassoBuilder
                            .build()
                            .load(item.tease)
                            .priority(Picasso.Priority.NORMAL)
                            .placeholder(R.drawable.tease_square_placeholder)
                            .fit()
                            .centerCrop()
                            .into(viewHolder.teaseImage)
                }

                viewHolder.headlineText.text = item.headline
            }

            is NewsItemVideoHolder -> {
                if (item.tease.isNotEmpty()) {
                    picassoBuilder
                            .build()
                            .load(item.tease)
                            .priority(Picasso.Priority.HIGH)
                            .placeholder(R.drawable.tease_rect_placeholder)
                            .fit()
                            .centerCrop()
                            .into(viewHolder.teaseImage)
                }

                viewHolder.summaryText.text = item.summary
                viewHolder.headlineText.text = item.headline
                viewHolder.videoUri = Uri.parse(item.videoPreviewUrl)
                viewHolder.videoVolumePill.visibility = View.VISIBLE
            }
        }
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }
}
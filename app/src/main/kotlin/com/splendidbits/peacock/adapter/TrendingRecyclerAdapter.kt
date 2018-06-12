package com.splendidbits.peacock.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.splendidbits.peacock.R
import com.splendidbits.peacock.helpers.*
import com.splendidbits.peacock.model.Item
import com.splendidbits.peacock.view.AbstractNewsItemHolder
import com.splendidbits.peacock.view.NewsItemArticleHolder
import com.splendidbits.peacock.view.NewsItemFeaturedHolder
import com.splendidbits.peacock.view.NewsItemVideoHolder
import com.squareup.picasso.Picasso

class TrendingRecyclerAdapter(val context: Context, private val picasso: Picasso) :
        ListAdapter<Item, AbstractNewsItemHolder>(diffCallback) {
    private val TYPE_ARTICLE: Int = 1
    private val TYPE_VIDEO: Int = 2
    private val TYPE_FEATURED: Int = 3
    private val imageHelper = ImageHelper()

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                    oldItem.itemId == newItem.itemId

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractNewsItemHolder {
        when (viewType) {
            TYPE_FEATURED -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_news_featured, parent, false)
                return NewsItemFeaturedHolder(itemView)
            }
            TYPE_VIDEO -> {
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
        return getItem(position).externalId.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position)?.featured ?: false) {
            TYPE_FEATURED
        } else if (getItem(position)?.hasVideo() ?: false) {
            TYPE_VIDEO
        } else {
            TYPE_ARTICLE
        }
    }

    override fun onBindViewHolder(viewHolder: AbstractNewsItemHolder, position: Int) {
        val item = getItem(position)
        viewHolder.item = item

        val heroImageUrl = item.assets.getHeroImage()
        val thumbImageUrl = item.assets.getThumbnailImage()

        if (viewHolder is NewsItemFeaturedHolder) {
            viewHolder.pillText.text = context.getString(R.string.breaking)
            viewHolder.headlineText.text = item.title
            viewHolder.summaryText.text = item.summary

            if (item.hasImage()) {
                picasso.load(heroImageUrl)
                        .placeholder(R.drawable.tease_rect_placeholder)
                        .fit()
                        .centerCrop()
                        .into(viewHolder.teaseImage)
            }
        }

        if (viewHolder is NewsItemVideoHolder) {
            if (item.hasVideo()) {
                viewHolder.videoUri = Uri.parse(item.assets.getFirstVideoUrl())
            }
            viewHolder.pillText.visibility = View.VISIBLE
            viewHolder.summaryText.text = item.summary

            if (item.hasImage()) {
                picasso.load(heroImageUrl)
                        .placeholder(R.drawable.tease_rect_placeholder)
                        .fit()
                        .centerCrop()
                        .into(viewHolder.teaseImage)
            }
        }

        if (viewHolder is NewsItemArticleHolder) {
            if (item.hasImage()) {
                picasso.load(thumbImageUrl)
                        .placeholder(R.drawable.tease_square_placeholder)
                        .fit()
                        .centerCrop()
                        .into(viewHolder.teaseImage)
            }
        }

        viewHolder.headlineText.text = item.title
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }
}
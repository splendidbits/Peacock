
package com.splendidbits.peacock.view

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.RecyclerView
import android.view.View
import com.splendidbits.peacock.R
import com.splendidbits.peacock.enums.NewsType
import com.splendidbits.peacock.model.NewsItem



abstract class AbstractNewsItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var newsItem: NewsItem ?= null

    init {
        itemView.setOnClickListener({
            if (newsItem?.type == NewsType.TYPE_ARTICLE || newsItem?.type == NewsType.TYPE_FEATURED) {
//                val bundle = Bundle()
//                bundle.putString("url", newsItem?.url)
//                it.findNavController().navigate(R.id.action_trendingFragment_to_readFragment, bundle)

                if (newsItem?.url?.isNotEmpty() ?: false) {
                    val customTabsIntent = CustomTabsIntent.Builder()
                            .setToolbarColor(itemView.getResources().getColor(R.color.peacock_blue))
                            .setShowTitle(true)
                            .build()

                    customTabsIntent.launchUrl(itemView.context, Uri.parse(newsItem?.url))
                }
            }
        })
    }
}
package com.splendidbits.peacock.view

import android.net.Uri
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.splendidbits.peacock.enums.ItemType
import com.splendidbits.peacock.model.Item
import kotlinx.android.synthetic.main.view_holder_news_video.view.*

abstract class AbstractNewsItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var item: Item? = null
    val teaseImage = itemView.teaseImage
    val headlineText = itemView.headline

    init {
        itemView.setOnClickListener({
            if (item != null && item?.url?.isNotEmpty() ?: false &&
                    (item?.type == ItemType.TYPE_ARTICLE || item?.type == ItemType.TYPE_ARTICLE)) {

                val customTabsIntent = CustomTabsIntent.Builder()
                        .setToolbarColor(itemView.getResources().getColor(android.R.color.white))
                        .setShowTitle(true)
                        .build()

                customTabsIntent.launchUrl(itemView.context, Uri.parse(item?.url))
            }
        })
    }
}
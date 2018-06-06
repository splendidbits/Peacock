package com.splendidbits.peacock.view

import android.view.View
import kotlinx.android.synthetic.main.view_holder_news_featured.view.*

class NewsItemFeaturedHolder(itemView: View) : AbstractNewsItemHolder(itemView) {
    val teaseImage = itemView.teaseImage
    val headlineText = itemView.headline
    val summaryText = itemView.summary
    val pillText = itemView.pillText
}
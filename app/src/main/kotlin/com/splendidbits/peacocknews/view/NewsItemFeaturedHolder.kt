package com.splendidbits.peacocknews.view

import android.view.View
import kotlinx.android.synthetic.main.view_holder_news_featured.view.*

class NewsItemFeaturedHolder(itemView: View) : AbstractNewsItemHolder(itemView) {
    val pillText = itemView.pillText
    val summaryText = itemView.summary
}
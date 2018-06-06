
package com.splendidbits.peacock.view

import android.view.View
import kotlinx.android.synthetic.main.view_holder_news_featured.view.*

class NewsItemArticleHolder(itemView: View) : AbstractNewsItemHolder(itemView) {
    val teaseImage = itemView.teaseImage
    val headlineText = itemView.headline
}
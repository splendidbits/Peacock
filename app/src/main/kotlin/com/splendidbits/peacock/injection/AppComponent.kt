package com.splendidbits.peacock.injection

import com.splendidbits.peacock.activity.NewsActivity
import com.splendidbits.peacock.adapter.TrendingRecyclerAdapter
import com.splendidbits.peacock.fragment.ArticleFragment
import com.splendidbits.peacock.fragment.TrendingFragment
import com.splendidbits.peacock.main.PeacockApplication
import com.splendidbits.peacock.view.NewsItemVideoHolder
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, ComponentModule::class])
interface AppComponent {
    fun inject(application: PeacockApplication)

    fun inject(newsActivity: NewsActivity)

    fun inject(fragment: TrendingFragment)
    fun inject(fragment: ArticleFragment)

    fun inject(recyclerAdapter: TrendingRecyclerAdapter)
    fun inject(viewHolder: NewsItemVideoHolder)
}
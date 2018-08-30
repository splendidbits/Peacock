package com.splendidbits.peacocknews.injection

import com.splendidbits.peacocknews.activity.NewsActivity
import com.splendidbits.peacocknews.adapter.TrendingRecyclerAdapter
import com.splendidbits.peacocknews.fragment.ArticleFragment
import com.splendidbits.peacocknews.fragment.TrendingFragment
import com.splendidbits.peacocknews.main.PeacockApplication
import com.splendidbits.peacocknews.view.NewsItemVideoHolder
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